package edu.kit.pse.bdhkw.client.controller;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.kit.pse.bdhkw.BuildConfig;
import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.communication.Response;

/**
 * Created by Tarek on 08.02.2017.
 * Background service for any Communication with the GoAppServer
 * Serializes Request objects to JSON Strings and sends the via HTTP-POST
 * to a pre-defined web-server.
 * Still TODO: move server-URL to sharedPreferences
 */

public class NetworkIntentService extends IntentService {

    // TODO: move to sharedPrefs
    private static final int DOWNLOAD_BUFFER_SIZE = 2048;
    private static final String LOG_TAG = NetworkIntentService.class.getSimpleName();
    private static final String RESPONSE_TAG = "res";
    private static final String REQUEST_TAG = "req";
    private static final String BROADCAST_RESULT = BuildConfig.APPLICATION_ID + ".Result";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SERVER_URL = "https://i43pc164.ipd.kit.edu/PSEWS1617GoGruppe3/server/GoAppServer/";

    public NetworkIntentService() {
        super(NetworkIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Get extra from intent (request object)
        Request request = (Request) intent.getExtras().get(REQUEST_TAG);

        Log.d(LOG_TAG, "intent received: " + request.getSenderDeviceId());

        // Send the request and get the response in return.
        Response response = sendRequest(request);
        Intent result = new Intent(BROADCAST_RESULT);

        intent.putExtra(RESPONSE_TAG, response);

        // Broadcast response to anyone listening.
        LocalBroadcastManager.getInstance(this).sendBroadcast(result);
    }

    /**
     * Sends a Request object to the specified URL via HTTP-POST.
     * Returns the server response on success.
     * @param request to be sent to the server.
     * @return response that was received from the server.
     */
    private Response sendRequest(Request request) {
        InputStream inputStream;
        OutputStream outputStream;
        Response response = null;
        try {
            // Establish connection
            URL url = new URL(SERVER_URL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            outputStream = urlConnection.getOutputStream();

            Log.d(LOG_TAG, "Connection established!");

            // Serialize request object
            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, request);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            // Write output
            outputStream.write(writer.toString().getBytes());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            Log.d(LOG_TAG, "Request sent!");

            // Get the response
            inputStream = urlConnection.getInputStream();
            int read = 0;
            byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
            String input = new String();
            while((read = inputStream.read(buffer)) > 0) {
                byte[] used;
                if (read < buffer.length) {
                    used = new byte[read];
                    System.arraycopy(buffer, 0, used, 0, used.length);
                } else {
                    used = buffer;
                }
                input += new String(used);
            }
            inputStream.close();

            Log.d(LOG_TAG, "Response: " + input);
            response = objectMapper.readValue(input.getBytes(), Response.class);

            Log.d(LOG_TAG, "success: " + response.getSuccess());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
