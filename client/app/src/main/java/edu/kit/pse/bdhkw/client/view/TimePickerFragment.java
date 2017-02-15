package edu.kit.pse.bdhkw.client.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

import edu.kit.pse.bdhkw.client.controller.database.GroupService;
import edu.kit.pse.bdhkw.client.model.objectStructure.Appointment;
import edu.kit.pse.bdhkw.client.model.objectStructure.GroupClient;

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

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //TODO ich hoffe das passt so. Bitte überprüfen
        String groupName = ((BaseActivity) getActivity()).getGroupname();
        GroupService groupService = new GroupService(this.getContext());
        GroupClient groupClient = groupService.readOneGroupRow(groupName);
        String hour = String.valueOf(hourOfDay);
        String min = String.valueOf(minute);
        groupClient.getAppointment().getAppointmentDate().setTime(hour + ":" + min);

        //TODO: speichere time in gesondertem Appointment <-- auch hier keine Ahnung was das bedeuten soll (lg Theresa)
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        //TODO: herausfinden woher diese Methode kommt
        //  newFragment.show(getSupportFragmentManager(), "timePicker");
    }

}