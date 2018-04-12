package com.example.yeah1.sitter.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yeah1.sitter.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewDialogFragment extends DialogFragment implements View.OnClickListener {

    private View rootView;
    private TextView textViewName;

    private Button buttonInsert;
    private ImageButton imageButtonClose;

    private float rate;
    private String content;
    private String oldContent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReviewDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewDialogFragment newInstance(String param1, String param2) {
        ReviewDialogFragment fragment = new ReviewDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_dialog, container, false);

        ReviewDialogFragment.this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        textViewName = (TextView) rootView.findViewById(R.id.textview_sitter_name);
        textViewName.setText(mParam2 + "님에 대한 후기를 남겨주세요 !");

        buttonInsert = (Button) rootView.findViewById(R.id.button_write_review);
        buttonInsert.setOnClickListener(this);
        imageButtonClose = (ImageButton) rootView.findViewById(R.id.button_close);
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_write_review:
                content = ((EditText) rootView.findViewById(R.id.edittext_write_review)).getText().toString();
                rate = ((RatingBar) rootView.findViewById(R.id.ratingbar_write_review)).getRating();
                if (content.equals("")) {
                    Toast.makeText(getActivity(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rate == 0.0) {
                    Toast.makeText(getActivity(), "평점을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mListener != null) {
                    mListener.onFragmentInteraction(mParam1, content, rate);
                }
                break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String sitterEmail, String contents, float rate);
    }
}
