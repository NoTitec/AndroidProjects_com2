package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtMile=findViewById(R.id.edtMile);
        TextView txtResult= findViewById((R.id.txtResult));
        Button calculate=findViewById(R.id.btnOK);

        calculate.setOnClickListener(new View.OnClickListener() {//button객체의 아버지가 view 객체 이벤트 처리 일반적인 방법
            @Override
            public void onClick(View view) {
                String input=txtMile.getText().toString();
                float f= (Float.parseFloat(input)*1.6f);

                txtResult.setText(String.valueOf(f));
            }
        });
    }
}