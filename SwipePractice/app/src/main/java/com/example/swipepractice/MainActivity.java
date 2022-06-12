package com.example.swipepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int PAGE_COUNT=4;//페이지 수

    private ViewPager2 viewPager2;// 메인레이아웃 뷰페이져
    private FragmentStateAdapter pagerAdapter; // viewpager2에 페이지 연결

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter{
        //간단한 페이지 어댑터 메인레이아웃의 ViewPager2에서 표시할 페이지들 저장
        public ScreenSlidePagerAdapter(FragmentActivity fa){//FragmentStateAdapter는 FragmentActivity인자요구
            super(fa);
        }
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new Page1();
                case 1 :
                    return new Page2();
                case 2 :
                    return new Page3();
                case 3:
                    return new Page4();
            }
            return new Page2();
        }

        @Override
        public int getItemCount() {
            return PAGE_COUNT;//페이지 수 리턴
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.pager);// 메인 페이지뷰2가져옴
        pagerAdapter = new ScreenSlidePagerAdapter(this);//FragmentActivity 인자로 새로운 객체생성,이는 페이지뷰의 소유 엑티비티가 누구인지 알려주는것
        viewPager2.setAdapter(pagerAdapter);

    }
}