package com.example.swipe;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Page1 extends Fragment {
    private PageViewModel pageViewModel;
    EditText nameEditText;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // initialise ViewModel here
        pageViewModel = new ViewModelProvider(requireActivity())
                .get(PageViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  (ViewGroup) inflater.inflate(
                R.layout.slide_page, container, false);

        //TextView tv = (TextView)view.findViewById(R.id.txtData);
        nameEditText = (EditText) view.findViewById(R.id.txtData);
        nameEditText.setText("Sample Page 1");
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add Text Watcher on name input text
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pageViewModel.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}