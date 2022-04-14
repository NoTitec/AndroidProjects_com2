package com.example.statecheckapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {// 앱시작시 실행1
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("!!!!!!!!!!!!!!!!!!!","Oncreate called");
    }
    @Override
    protected void onStart() {//앱시작시 실행2 백그라운드에서 돌아올때실행2
        super.onStart();
// The activity is about to become visible.
        Log.e("!!!!!!!!!!!!!!!!!!!!","Onstart called");
    }
    @Override
    protected void onRestart() {//앱 백그라운드에서 다시킬때 실행
        super.onRestart();
// The activity is between stopped and started.
        Log.e("!!!!!!!!!!!!!!!!!!!!","Onrestart called");
    }
    @Override
    protected void onResume() {//앱 시작시 실행3 백그라운드에서 돌아올때 실행3
        super.onResume();
// The activity has become visible
// it is now "resumed"
        Log.e("!!!!!!!!!!!!!!!!!!!!","Onresume called");
    }
    @Override
    protected void onPause() {//앱 백그라운드갈때 실행
        super.onPause();
// Another activity is taking focus
// this activity is about to be "paused"
        Log.e("!!!!!!!!!!!!!!!!!!!!","Onpause called");
    }
    @Override
    protected void onStop() {//앱 백그라운드갈때 실행
        super.onStop();
// The activity is no longer visible
// it is now "stopped"
        Log.e("!!!!!!!!!!!!!!!!!!!!","Onstop called");
    }
    @Override
    protected void onDestroy() {//앱 종료될때 실행
        super.onDestroy();
// The activity is about to be destroyed.
        Log.e("!!!!!!!!!!!!!!!!!!!!","Ondestroy called");
    }
}