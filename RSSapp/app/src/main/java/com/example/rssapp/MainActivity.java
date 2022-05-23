package com.example.rssapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // this class represents the main GUI
    // TODO: use a resource file rather than hard-coding
    //       URLs into the array myUrlAddressCaption

    ArrayAdapter<String> adapterMainSubjects;//리스트뷰가 사용할 어댑터
    ListView myMainListView;//리스트뷰 변수
    Context  context;//context담을 변수
    SingleItem selectedNewsItem;

    String [][] myUrlAddressCaption = {//각 사이트 RSS 링크 정보와 리스트뷰에 보여질 String 내용
            {"https://feeds.bbci.co.uk/news/rss.xml", "Top Stories"},
            {"https://feeds.bbci.co.uk/news/world/rss.xml", "World"},
            {"https://feeds.bbci.co.uk/news/health/rss.xml", "Health"},
            {"https://feeds.bbci.co.uk/news/technology/rss.xml", "Technology"},
            {"https://feeds.bbci.co.uk/news/science_and_environment/rss.xml", "Science & Environment"},
            {"https://feeds.bbci.co.uk/news/education/rss.xml", "Eucation & Family"},
            {"https://biz.heraldm.com/rss/010000000000.xml", 	 "해럴드 경제"}
/*		{"https://rss.joins.com/joins_news_list.xml",   "?꾩껜湲곗궗"},
		{"https://rss.joins.com/joins_homenews_list.xml",   "二쇱슂湲곗궗"},
		{"https://rss.joins.com/joins_money_list.xml", 	 "寃쎌젣"},
		{"https://rss.joins.com/joins_star_list.xml",   "?곗삁?꾩껜"},
		{"https://rss.joins.com/joins_it_list.xml",   "IT怨쇳븰"},
		{"https://rss.joins.com/joins_sports_list.xml", 	 "?ㅽ룷痢?},
		{"https://rss.joins.com/joins_culture_list.xml",   "臾명솕"},
		{"https://rss.joins.com/joins_life_list.xml",   "?ы쉶"}*/

            };

    //define convenient URL and CAPTIONs arrays
    String [] myUrlCaption = new String[myUrlAddressCaption.length];//텍스트부분저장 할 배열
    String [] myUrlAddress = new String[myUrlAddressCaption.length];//RSS 링크부분저장 할 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<myUrlAddressCaption.length; i++) {//RSS 주소와 해당 카테고리 이름 저장
            myUrlAddress[i] = myUrlAddressCaption[i][0];
            myUrlCaption[i] = myUrlAddressCaption[i][1];
        }
        // ---------------------------------------------------------------------

        context = getApplicationContext();//현재 main의 context 가져와서 저장
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d, yyyy ", Locale.US);
        this.setTitle("News Service\n" + sdf.format(new Date() ) );//앱이름 현재시간설정

        myMainListView = (ListView)this.findViewById(R.id.myListView);//리스트뷰불러옴
        myMainListView.setOnItemClickListener(new OnItemClickListener() {//리스트뷰에 이벤트리스너 등록
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = myUrlAddress[_index];//선택 RSS 링크
                String urlCaption = myUrlCaption[_index];//선택 카테고리 이름

                //create an Intent to talk to Activity2 리사이클러뷰 activity로 넘어감
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadlines.class);
                //prepare a Bundle and add the data pieces to be sent
                Bundle myData = new Bundle();//여러 정보를 담아 다른 곳으로 넘겨줄때 사용하는 클래스
                myData.putString("urlAddress", urlAddress);// 키 ,값으로 Bundle에 담음
                myData.putString("urlCaption", urlCaption);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);//activity 제어 showheadlines Intent 로 넘김
            }
        });

        // bind main category list (Top News, ...) to the the listView
        // show list and get ready for user to click on a category

        //ArrayAdapter를 기본제공 레이아웃으로 만들어 뉴스 이름문자열들을 이용해 어댑터를 생성하고 리스트뷰에 어댑터 연결
        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , myUrlCaption);//android.R.layout 은 미리 제공된 레이아웃
        myMainListView.setAdapter(adapterMainSubjects);

    }//onCreate

}