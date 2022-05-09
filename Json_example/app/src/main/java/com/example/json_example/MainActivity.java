package com.example.json_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // JSON Node names , json파일의 키이름
    private static final String TAG_CONTACTS = "contacts";

    private static final String TAG_NAME = "name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PHONE = "phone";

    // contacts JSONArray, json파일 전체
    JSONArray contacts = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hashmap for List, HashMap 키 String 값 String 요소들을 가지는 ArrayList
        //HashMap은 키,값형태의 자료구조이며 json의 구조또한 키,값이기때문에 json처리에는 HashMap을 사용하면 편리하다
        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

        // getting JSON string from text file
        JSONObject json = null;

        String ret = "";

        try {
            //InputStream inputStream = openFileInput("json1.txt"); java의경우
            InputStream inputStream = getResources().openRawResource(R.raw.json1);//안드로이드 raw밑에 json1로드 이건 파일이아님

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {//객체 다음줄 읽을거 없을때까지 다 읽어서 문자열싹다가져와 ret에 저장
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
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
            contacts = json.getJSONArray(TAG_CONTACTS);//json contacts키의 값인 jsonarray가져옴

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);//1개 오브젝트가져옴

                // Storing each json item in variable

                String name = c.getString(TAG_NAME);//오브젝트의 이름추출
                String email = c.getString(TAG_EMAIL);//오브젝트의 이메일추출
                String phone = c.getString(TAG_PHONE);//오브젝트의 전화번호추춝

                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();

                // adding each child node to HashMap key => value

                map.put(TAG_NAME, name);
                map.put(TAG_EMAIL, email);
                map.put(TAG_PHONE, phone);

                // adding HashList to ArrayList
                contactList.add(map);//만든 HashMap을 ArrayList에 추가
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Now put parsed result to TextView
        TextView res = findViewById(R.id.txtResult);//textview객체 가져옴
        String ss = "";
        for (int i = 0; i < contactList.size(); i++) {//html 형식으로 태그와 태그에 해당하는 문자열 넣어 html문서 형식 txt파일 완성
            ss += "<h1>";
            ss += contactList.get(i).get(TAG_NAME);
            ss += "<h3>";
            ss += contactList.get(i).get(TAG_EMAIL);
            ss += "<h3>";
            ss += contactList.get(i).get(TAG_PHONE);
            ss += "<br><br>";
        }
        res.setMovementMethod(new ScrollingMovementMethod()); //enable scrolling textview에 스크롤 기능 활성화
        res.setText(HtmlCompat.fromHtml(ss, HtmlCompat.FROM_HTML_MODE_COMPACT)); //html text로 string을 변환
    }
}