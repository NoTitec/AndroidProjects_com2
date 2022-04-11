package com.example.radioselectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;

import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {//액티비티하기위한 여러 가지 상태값보관하는 Bundle 클래스
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//R은 res폴더의 약자

        TextView result=findViewById(R.id.result);
        CheckBox addbutton=findViewById(R.id.addbutton);
        CheckBox nobutton=findViewById(R.id.nobutton);
        Button pay=findViewById(R.id.Pay);
        RadioButton r1=findViewById(R.id.americano);
        RadioButton r2=findViewById(R.id.cafuchino);
        RadioButton r3=findViewById(R.id.latte);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempresult="";

                if(r1.isChecked()){
                    tempresult+=r1.getText().toString();
                }
                if(r2.isChecked()){
                    tempresult+=r2.getText().toString();
                }
                if(r3.isChecked()){
                    tempresult+=r3.getText().toString();
                }
                if((addbutton.isChecked()&&nobutton.isChecked())||(!addbutton.isChecked()&&!nobutton.isChecked())) {
                    Log.e("error",tempresult);//logcat 에 빨간색으로 표기된다
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();//잠시나오는 메시지

                }
                else{
                    if (addbutton.isChecked()) {
                        tempresult += addbutton.getText().toString();
                        result.setText(tempresult);

                    } else if (nobutton.isChecked()) {
                        tempresult += nobutton.getText().toString();
                        result.setText(tempresult);

                    }
                }
            }
        });


    }
}