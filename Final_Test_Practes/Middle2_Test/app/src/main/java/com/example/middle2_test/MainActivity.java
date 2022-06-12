package com.example.middle2_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_PERSONS = "persons";

    private static final String TAG_NAME = "name";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_SMALL = "small";
    private static final String TAG_BIG = "big";
    private RecyclerView recyclerView;//리사이클러뷰 변수
    private RecyclerviewItemAdapter recyclerviewItemAdapter;//내가 정의한 리사이클러뷰 어댑터
    JSONArray persons=null;
    private List<Item> contactList;

    int small[]={
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s9
    };
    int big[]={
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.b5,
            R.drawable.b6,
            R.drawable.b7,
            R.drawable.b8,
            R.drawable.b9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<Item>();


        JSONObject json = null;

        String ret = "";

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.json2);//안드로이드 raw밑에 json1로드 이건 파일이아님

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";//한줄 저장할 String
                StringBuilder stringBuilder = new StringBuilder();//전체 json.txt 문자열 저장할 StringBuilder

                while ((receiveString = bufferedReader.readLine()) != null) {//객체 다음줄 읽을거 없을때까지 다 읽어서 문자열싹다가져와 ret에 저장
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();//ret String에 읽은 json.txt내용 저장
            }
        } catch (FileNotFoundException e) {
            Log.e("JSON Parser", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("JSON Parser", "Can not read file: " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            json = new JSONObject(ret);//ret 문자열을 json오브젝트로 만들어줌
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }


        try {//json오브젝트에서 원하는데이터를 추출
            // Getting Array of Contacts
            persons = json.getJSONArray(TAG_PERSONS);//json contacts키의 값인 jsonarray가져옴

            // looping through All Contacts
            for (int i = 0; i < persons.length(); i++) {
                JSONObject c = persons.getJSONObject(i);//1개 오브젝트가져옴

                String name = c.getString(TAG_NAME);//오브젝트의 이름추출
                JSONObject image=c.getJSONObject(TAG_IMAGE);//json안에 json오브젝트 추출
                String smalli=image.getString(TAG_SMALL);
                int smalliint=Integer.parseInt(smalli);
                System.out.println(smalliint);
                String bigi=image.getString(TAG_BIG);
                int bigiint=Integer.parseInt(bigi);
                System.out.println(bigiint);
                Item item=new Item(name,small[smalliint-1],big[bigiint-1]);
                // adding each child node to HashMap key => value

                // adding HashList to ArrayList
                contactList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerviewItemAdapter = new RecyclerviewItemAdapter(contactList, this);//getApplicationContext() this로 main Context안주면 오류생김 왜?
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);
        recyclerviewItemAdapter.notifyDataSetChanged();
    }

}