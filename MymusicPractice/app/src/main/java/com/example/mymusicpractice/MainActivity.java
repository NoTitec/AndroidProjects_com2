package com.example.mymusicpractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {//로더 백그라운드 스레드 반환타입을 ?에

    private static final int LOADER_ID = 10000;//로더 식별 번호
    private LoaderManager loaderManager;//로더 매니저 변수
    // contacts JSONArray
    JSONArray contacts = null;//jsonarray변수
    private List<ContactsItems> itemsList = new ArrayList<ContactsItems>();//실제 데이터 보관할 리스트

    private static final String NAME = "artistName";
    private static final String IMAGE = "artworkUrl100"; //image
    private static final String COLNAME = "trackName";
    private RecyclerView recyclerView;//리사이클러뷰 변수
    private RecyclerviewItemAdapter recyclerviewItemAdapter;//리사이클러뷰어댑터 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);//리사이클러뷰 레이아웃 가져옴

        this.loaderManager = LoaderManager.getInstance(this);//로더 객체 가져옴

        Loader<String> loader = loaderManager.initLoader(LOADER_ID, null, (LoaderManager.LoaderCallbacks<String>)this);//로더 초기화
        loader.forceLoad();//로더 시작
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {//asnctaskloader상속받은 백그라운드 객체 생성
        return new FetchData(getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String str) {//백그라운드 객체에서 수행할 동작 정의 1번째 인자는 onCreateLoader 반환타입 ,Object 인자는 OnCreateLoader의 FetchData가 반화하는 데이터 타입
        JSONObject json = null;
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        try {
            // Getting Array of Contacts
            contacts = json.getJSONArray("results");
            Log.e("++++++++++", "Len = " + contacts.length() );
            // looping through All Contacts
            for(int i = 0; i < contacts.length(); i++){
                JSONObject c = contacts.getJSONObject(i);

                // Storing each json item in variable
                String name = c.getString(NAME);
                String image = c.getString(IMAGE); //image
                String colname = c.getString(COLNAME);

                ContactsItems items = new ContactsItems(image, name, colname);
                itemsList.add(items);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerviewItemAdapter = new RecyclerviewItemAdapter(itemsList, this);//getApplicationContext() this로 main Context안주면 오류생김 왜?
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);
        recyclerviewItemAdapter.notifyDataSetChanged();

    }
    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
    private static class FetchData extends AsyncTaskLoader<String> {

        public FetchData(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;
            String line;
            try {
                URL url = new URL("https://itunes.apple.com/search?term=simon");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return null;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) buffer.append(line);

                if (buffer.length() == 0) return null;
                jsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivity", "Error closing stream", e);
                    }
                }
            }

            return jsonStr;
        }

        @Override
        public void deliverResult(String data) {
            super.deliverResult(data);
        }
    }
}