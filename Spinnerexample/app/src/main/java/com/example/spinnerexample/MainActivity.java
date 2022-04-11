package com.example.spinnerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       txt=findViewById(R.id.text);
        Spinner spinner =(Spinner) findViewById(R.id.spn);
        if(spinner!=null){//spinner객체가 null이 아니라면
            //리스너 등록방법
            //1.new 리스터객체 넘겨주어 등록하고 내부에서 구현코드작성
            //2. 앱에 리스너 인터페이스 확장하여 메소드로 만들어 등록 (이프로젝트는 이방법 구현)
            spinner.setOnItemSelectedListener(this);//필요한 메소드가 현재 클래스 내부에있음

        }
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.labels_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {//AdapterView<?> 는 제네릭 뭐가오든 받아들이겠다
        String select_item=adapterView.getItemAtPosition(i).toString();
        txt.setText(select_item);
        //working code
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}