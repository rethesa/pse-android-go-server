package edu.kit.pse.bdhkw.client.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dennisbauml on 12.01.17.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.PersonViewHolder> {

    //TODO: nicht implementieren
    private String[] mDataset;


    //android best practice..
    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public TextView personsName;
        public CardView cardview;

        public PersonViewHolder(View v) {
            super(v);
            //mTextView = v;
            this.cardview = (CardView) itemView.findViewById(edu.kit.pse.bdhkw.R.id.cardview);
            this.personsName = (TextView) itemView.findViewById(edu.kit.pse.bdhkw.R.id.person_name);
        }

    }
    //.. best practice !

    // Provide a suitable constructor (depends on the kind of dataset)
    public MemberAdapter(String[] groups) {
        mDataset = groups;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(edu.kit.pse.bdhkw.R.layout.cardview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        PersonViewHolder vh = new PersonViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.personsName.setText(mDataset[i]);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    /*
    private String[] getMemberList(int groupid) {
        //TODO: gets data from the android data base
        String[] bla = {"tarek" , "theresa", "victoria", "matthias", "dennis" , "bla" , "fisch", "alex", "mähhh", "bähhh", "hola", "    DFadf dnöfn "};
        return bla;
    }
    */


}