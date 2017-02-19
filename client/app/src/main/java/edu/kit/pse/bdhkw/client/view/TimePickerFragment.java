package edu.kit.pse.bdhkw.client.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.kit.pse.bdhkw.R;
import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    /**
     * onTimeSet returns the values the user choosed in the dialog fragment.
     *
     * @param view TimePicker
     * @param hourOfDay int
     * @param minute int
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.selectedHour), hourOfDay);
        editor.putInt(getString(R.string.selectedMin), minute);
        editor.commit();
    }

}