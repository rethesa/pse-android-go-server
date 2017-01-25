package edu.kit.pse.bdhkw.client.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Schokomonsterchen on 12.01.2017.
 */

public class GroupAppointmentFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(edu.kit.pse.bdhkw.R.layout.group_appointment_fragment, container, false);

        view.findViewById(edu.kit.pse.bdhkw.R.id.groupname_button).setOnClickListener(this);
        view.findViewById(edu.kit.pse.bdhkw.R.id.appointment_button).setOnClickListener(this);
        view.findViewById(edu.kit.pse.bdhkw.R.id.time_button).setOnClickListener(this);
        view.findViewById(edu.kit.pse.bdhkw.R.id.date_button).setOnClickListener(this);
        view.findViewById(edu.kit.pse.bdhkw.R.id.place_button).setOnClickListener(this);
        view.findViewById(edu.kit.pse.bdhkw.R.id.next_appointment_button).setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        Fragment groupMapFragment;
        if (goStatus()) {
            groupMapFragment = new GroupMapGoFragment();
        } else {
            groupMapFragment = new GroupMapNotGoFragment();
        }
        if (edu.kit.pse.bdhkw.R.id.groupname_button == id) {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.group_container, new GroupMembersFragment())
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (edu.kit.pse.bdhkw.R.id.appointment_button == id) {
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (edu.kit.pse.bdhkw.R.id.time_button == id) {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            timePickerFragment.showTimePickerDialog(view);
        } else if (edu.kit.pse.bdhkw.R.id.date_button == id) {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.showDatePickerDialog(view);
        } else if (edu.kit.pse.bdhkw.R.id.place_button == id) {
/*            Intent intent = new Intent(this.getActivity(), UsernameActivity.class);
            intent.putExtra("OpenFirstTime", "false");
            this.getActivity().startActivity(intent);
  */          //TODO: speichere place
        } else if (edu.kit.pse.bdhkw.R.id.next_appointment_button == id) {
            //TODO: Verändere String "Mustertreffen"
            getFragmentManager().beginTransaction()
                    .replace(edu.kit.pse.bdhkw.R.id.group_container, groupMapFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

    }

    private boolean goStatus() {
        //TODO: überprüfen, ob go gedrückt ist
        return false;
    }


}