package com.example.asynctask_example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private TextView tvJsonResult;
    //LoaderManager.LoaderCallbacks<String> loaderCallbacks = this;
    private static final int LOADER_ID_USERACCOUNT = 10000;//백그라운드 작업 번호
    private LoaderManager loaderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJsonResult = findViewById(R.id.tv_json_result);

        this.loaderManager = LoaderManager.getInstance(this);//로드매니저 객체 가져오기

        loaderManager.initLoader(//가져온객체 초기화 첫번째는 내가만든 이름값(내앱안에 로드가 여러개있으면 구분이 필요함), 두번째는 null, 세번째는 현재의 콜백함수 이름 지정
                LOADER_ID_USERACCOUNT,
                null,
                (LoaderManager.LoaderCallbacks<String>)this).forceLoad();//forceload 앞까지는 초기화고 forceload 를 호출하면 FetchData 객체를 생성하고 loadinbackground를 호출함
        //tvJsonResult.setMovementMethod(new ScrollingMovementMethod());
    }

    //LoaderManager implements시 구현해야하는 매소드 3개
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {//백그라운드 작업을 호출할 준비
        return new FetchData(getApplicationContext()); //this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {//백그라운드 작업 끝나면 자동으로 리턴데이터가 두번째 파라미터로 넘어옴
        tvJsonResult.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {//정교한 앱이 아닌이상 필요없음
    }

/////////////////////////
private static class FetchData extends AsyncTaskLoader<String> {

    public FetchData(Context context) {//부모의 context를 가져와 활용하는 코드지만 이 백그라운드 작업은 context로 아무것도 안한다
        super(context);
    }

    @Override
    public String loadInBackground() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;
        String line;
        try {
            URL url = new URL("https://itunes.apple.com/search?term=avicii&limit=2");
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
/////////////////////////


}