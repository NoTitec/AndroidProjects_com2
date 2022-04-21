package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<String> mWordList = new ArrayList<>();
    private RecyclerView mRecyclerView;//객체를 전역변수로
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        populateWord();//데이터준비


// Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList);//어댑터 클래스정의에 현재액티비티 context 와 데이터넘겨서 제작
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);//어댑터연결
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//리사이클러뷰의 레이아웃 설정 3가지중 하나
    }

    String words[] = {
            "approach",
            "respond",
            "implement",
            "identify",
            "specify",
            "categorize",
            "pursue",
            "grant",
            "attribute",
            "detect",
            "following",
            "adapter",
            "preference",
            "task",
            "violet",
            "iris",
            "hybrid",
            "charge",
            "share",
            "diagram",
            "guilty",
            "enforce",
            "interactive"
    };

    private void populateWord() {
        // fill words
        for (int i = 0; i < words.length; i++) {
            mWordList.add(words[i]);
        }
    }
}