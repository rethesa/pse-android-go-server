package edu.kit.pse.bdhkw.client.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.BuildConfig;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

import edu.kit.pse.bdhkw.client.communication.Request;
import edu.kit.pse.bdhkw.client.communication.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by Tarek on 13.01.17.
 */

public class NetworkIntentService extends IntentService {
    public static final String BROADCAST_RESULT = BuildConfig.APPLICATION_ID + ".Result";
    private static ObjectMapper objectMapper;

    public NetworkIntentService() {
        super(NetworkIntentService.class.getSimpleName());
        objectMapper = new ObjectMapper();
    }

    private static void start(Context context) {
        Log.i(NetworkIntentService.class.getSimpleName(), "Starting... #" + Thread.currentThread().getId());
        Intent intent = new Intent(context, NetworkIntentService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private Response sendRequest(Request request) {
        InputStream inputStream = null;
        try {

            // TODO: get URL from preferences + get servlet from request + query string
            URL url = new URL("http://localhost:8080/tomcat-servlet/");
            URLConnection connection = url.openConnection();

            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

            // Object to JSON
            StringWriter stringWriter = new StringWriter();
            objectMapper.writeValue(stringWriter, request);

            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(stringWriter.toString());
            out.close();

            // Process response
            // JSON is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"), 8);

            int read = 0;
            byte[] buffer = new byte[512];

            while ((read = inputStream.read(buffer)) > 0)
            {
                byte[] used;
                if (read < buffer.length) {
                    used = new byte[read];
                    System.arraycopy(buffer, 0, used, 0, used.length);
                } else {
                    used = buffer;
                }
                // Write used to stream here.
            }
            // return the parsed stuff as response object
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        return null;
    }

}

