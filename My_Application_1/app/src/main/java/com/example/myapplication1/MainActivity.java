package com.example.myapplication1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {//AppCompatActivity는 Activity 에서 발전된 클래스
    int count=0;
    TextView txtCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//AppCompatActivity override Bundle 객체는 실행환경
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//AppCompatActivity에있는 메소드 R은 res폴더의 xml문서를 c++의 include처럼 끌고과서 객체로 바꾸는 작업을 진행함
        //위 setcontent로 화면을 띄우고 그다음부터 이벤트처리 메소드를 처리하는 메소드를 작성한다
        txtCount=findViewById(R.id.txtCount);//textview id 객체 가져옴 이 위치가중요하다 이게 save정보 로드로직보다 아래있으면 다시 초기상태를 덮어씌워버린다
        if(savedInstanceState!=null){
            String scount=savedInstanceState.getString("curcount");
            if(txtCount!=null) {
                count=Integer.valueOf(scount);
                txtCount.setText(String.valueOf(count));
            }
        }

        Button clickbutton=findViewById(R.id.btnClick);//button객체 가져옴
        Button resetbutton=findViewById(R.id.resetClick);//reset button
        //button눌려짐에따른 이벤트처리
        clickbutton.setOnClickListener(new View.OnClickListener() {//button에 리스너 등록
            @Override
            public void onClick(View view) {
                ++count;
                txtCount.setText(String.valueOf(count));//txtcount객체의 text변경메소드 추가
            }
        });

        resetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=0;
                txtCount.setText(String.valueOf(count));
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {//현재 state저장 메소드 configuration 변경시 자동호출

        super.onSaveInstanceState(outState);

        outState.putString("curcount",String.valueOf(txtCount.getText()));
    }
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences myPrefs = getSharedPreferences("mySaveStateArea", MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();//SharedPreferences의 Editor이용
        editor.putString("Counter", txtCount.getText().toString());//데이터타입에따라 추가
        editor.commit();//commit해야적용
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences myPrefs = getSharedPreferences("mySaveStateArea", MainActivity.MODE_PRIVATE);
        if(myPrefs!=null && myPrefs.contains("Counter")) {
            String uCount = myPrefs.getString("Counter", "");
            count = Integer.valueOf(uCount);
            txtCount.setText(uCount);
        }
    }


}