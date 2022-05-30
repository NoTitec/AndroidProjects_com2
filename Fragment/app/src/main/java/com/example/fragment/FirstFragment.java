package com.example.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends Fragment {
    View view;
    Button firstButton;
    TextView firstTxt;
    int count=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.frag_first, container, false);
// get the reference of Button
        firstButton = view.findViewById(R.id.secondButton);
        firstTxt =  view.findViewById(R.id.txtView);
// perform setOnClickListener on first Button
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// display a message by using a Toast
                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
                count++;
                firstTxt.setText("You clicked " + count);
            }
        });
        return view;
    }
}