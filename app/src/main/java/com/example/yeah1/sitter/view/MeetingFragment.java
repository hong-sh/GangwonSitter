package com.example.yeah1.sitter.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yeah1.sitter.R;


public class MeetingFragment extends DialogFragment implements View.OnClickListener{

    private View rootView;

    public MeetingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MeetingFragment newInstance() {
        MeetingFragment fragment = new MeetingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_meeting, container, false);
        ((Button)rootView.findViewById(R.id.button_ok)).setOnClickListener(this);
        ((ImageButton)rootView.findViewById(R.id.button_close)).setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
