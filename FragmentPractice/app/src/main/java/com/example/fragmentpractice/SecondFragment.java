package com.example.fragmentpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class SecondFragment extends Fragment {
    View view;
    Button secondbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.second_frag,container,false);
        secondbtn=view.findViewById(R.id.secondfragbutton);

        secondbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Second Fragment",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }
}
