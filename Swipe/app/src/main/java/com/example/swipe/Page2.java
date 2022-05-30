package com.example.swipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Page2 extends Fragment {
    private PageViewModel pageViewModel;
    private TextView txtName;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialise ViewModel here
        pageViewModel = new ViewModelProvider(requireActivity()).get(PageViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  (ViewGroup) inflater.inflate(
                R.layout.slide_page2, container, false);
        ImageView tv = (ImageView)view.findViewById(R.id.imgView);
        tv.setImageResource(R.drawable.ic_launcher_foreground);
        return view;
    }
    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = view.findViewById(R.id.textViewName);
        pageViewModel.getName().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtName.setText(s);
            }
        });

    }
}