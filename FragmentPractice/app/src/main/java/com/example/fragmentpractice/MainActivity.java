package com.example.fragmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button viewFirstFragment,viewSecondFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메인레이아웃 컨트롤 가져오기
        viewFirstFragment=findViewById(R.id.viewfirstfragbutton);
        viewSecondFragment = findViewById(R.id.viewsecondfragbutton);
        viewFirstFragment.setOnClickListener(this);
        viewSecondFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.viewfirstfragbutton:
                loadFragment(new FirstFragment());
                break;
            case R.id.viewsecondfragbutton:
                loadFragment(new SecondFragment());
                break;
        }
    }

    private void loadFragment(Fragment fragment){
        // create a FragmentManager 프레그먼트 매니저 생성
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment 프레그먼트 트랜젝션 시작
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.fraglayout, fragment);// 메인레이아웃에 프래그먼트 배치
        fragmentTransaction.commit(); // save the changes


    }
}