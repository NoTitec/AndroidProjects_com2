package com.example.fragmentpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {
    View view;
    Button firstButton;
    TextView firsttext;
    int count =0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag_first, container, false);
        firsttext=view.findViewById(R.id.fragonetextview);
        firstButton=view.findViewById(R.id.firstbtn);
        firstButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                count++;

                firsttext.setText("clicked"+count);
            }
        });

        return view;
    }
}
