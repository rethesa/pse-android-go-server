package edu.kit.pse.bdhkw.client.view;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        GroupClient groupClient = null; //TODO hier noch die richtige Gruppe holen zu der das appointment gehört
        String dd = String.valueOf(day);
        String mM = String.valueOf(month);
        String yYYY = String.valueOf(year);
        groupClient.getAppointment().getAppointmentDate().setDate(dd + "." + mM + "." + yYYY);

        /**

         String hour = String.valueOf(hourOfDay);
         String min = String.valueOf(minute);
         groupClien.getAppointment().getAppointmentDate().setTime(hour + ":" + min);
         */
        //TODO: speichere date in gesondertem Appointment
    }

    /*
    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        //TODO: herausfinden woher diese Methode kommt
        //newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    */
}