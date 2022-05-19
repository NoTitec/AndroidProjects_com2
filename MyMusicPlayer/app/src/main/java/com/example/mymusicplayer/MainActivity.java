package com.example.mymusicplayer;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 10000;
    private LoaderManager loaderManager;
    // contacts JSONArray
    JSONArray contacts = null;
    private List<ContactsItems> itemsList = new ArrayList<ContactsItems>();

    private static final String NAME = "artistName";
    private static final String IMAGE = "artworkUrl100"; //image
    private static final String COLNAME = "trackName";
    private RecyclerView recyclerView;
    private RecyclerviewItemAdapter recyclerviewItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);

        this.loaderManager = LoaderManager.getInstance(this);

        Loader<String> loader = loaderManager.initLoader(LOADER_ID, null, (LoaderManager.LoaderCallbacks<String>)this);
        loader.forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {//asnctaskload 상속받은 객체하나만듬 Loader<?> 반환 ?는 객체가 반환하는 타입으로
        return new FetchData(getApplicationContext()); //this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String str) {//백그라운드 실행결과 받아서 처리하는 곳
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
        recyclerviewItemAdapter = new RecyclerviewItemAdapter(itemsList, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerviewItemAdapter);
        recyclerviewItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
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
/*
{
   "resultCount":50,
   "results":[
      {
         "wrapperType":"track",
         "kind":"song",
         "artistId":398120,
         "collectionId":303178981,
         "trackId":303178994,
         "artistName":"*NSYNC",
         "collectionName":"Celebrity",
         "trackName":"Pop",
         "collectionCensoredName":"Celebrity",
         "trackCensoredName":"Pop",
         "artistViewUrl":"https://music.apple.com/us/artist/nsync/398120?uo=4",
         "collectionViewUrl":"https://music.apple.com/us/album/pop/303178981?i=303178994&uo=4",
         "trackViewUrl":"https://music.apple.com/us/album/pop/303178981?i=303178994&uo=4",
         "previewUrl":"https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/c4/d3/1b/c4d31be9-04a8-649e-18f9-a8e33d368642/mzaf_2388649982457011349.plus.aac.p.m4a",
         "artworkUrl30":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/5f/b1/8e/5fb18eff-ecdf-f125-0c6a-f665d617126b/source/30x30bb.jpg",
         "artworkUrl60":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/5f/b1/8e/5fb18eff-ecdf-f125-0c6a-f665d617126b/source/60x60bb.jpg",
         "artworkUrl100":"https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/5f/b1/8e/5fb18eff-ecdf-f125-0c6a-f665d617126b/source/100x100bb.jpg",
         "collectionPrice":9.99,
         "trackPrice":1.29,
         "releaseDate":"2001-07-24T07:00:00Z",
         "collectionExplicitness":"notExplicit",
         "trackExplicitness":"notExplicit",
         "discCount":1,
         "discNumber":1,
         "trackCount":13,
         "trackNumber":1,
         "trackTimeMillis":238427,
         "country":"USA",
         "currency":"USD",
         "primaryGenreName":"Pop",
         "isStreamable":true
      },
      {
         "wrapperType":"track",
         "kind":"song",
         "artistId":398120,
         "collectionId":303095504,
 */