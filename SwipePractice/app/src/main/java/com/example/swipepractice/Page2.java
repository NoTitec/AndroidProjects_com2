package com.example.swipepractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class Page2 extends Fragment {
    private PageViewDataTransfer pageViewDataTransfer;
    private TextView txtName2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //프래그먼트 공유 객체 설정
        pageViewDataTransfer = new ViewModelProvider(requireActivity()).get(PageViewDataTransfer.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=(ViewGroup)inflater.inflate(R.layout.slide_page2,container,false);
        ImageView tv = view.findViewById(R.id.imageView);
        tv.setImageResource(R.drawable.ic_launcher_background);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName2=view.findViewById(R.id.textView2);
        //LiveData는 Observer인터페이스로 관찰, Observer인터페이스는 유일한 메서드인 onChanged가 있음
        //LiveData변경시 자동 콜백호출됨
        //LiveData.observe의 인자는 Owner Activity와 LiveData에맞는 Observer객체
        pageViewDataTransfer.getmName().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtName2.setText(s);
            }
        });
    }
}
