package com.example.swipepractice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Page1 extends Fragment {
    private PageViewDataTransfer pageViewDataTransfer;//페이지간 데이터 공유객체
    EditText nameEditText;//내용 변경 입력,수정 가능한 텍스트뷰
    @Override//페이지 프래그먼트 데이터 공유객체 설정
    public void onCreate(Bundle savedInstanceState){//프래그먼트 생성시 호출
        super.onCreate(savedInstanceState);
        // initialise ViewModel here
        pageViewDataTransfer = new ViewModelProvider(requireActivity())//액티비티 있다는걸 보장해주는 메소드가 requireActivity 보장없으면 getActivity
                .get(PageViewDataTransfer.class);
    }
    @Override//fragment뷰생성메소드 oncreate후 화면 구성할때 호출
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  (ViewGroup) inflater.inflate(
                R.layout.slide_page1, container, false);

        //TextView tv = (TextView)view.findViewById(R.id.txtData);
        nameEditText = (EditText) view.findViewById(R.id.textView);
        nameEditText.setText("Sample Page 1");
        return view;
    }
    @Override//onCreateView에서 리턴해준 view를 가지고 있음
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Add Text Watcher on name input text
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override//입력이될때 그내용을 실시간으로 공유데이터 객체 PageViewDataTransfer에 전달
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pageViewDataTransfer.setmName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
