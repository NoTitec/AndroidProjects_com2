package com.example.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * 데이터 생성
 * 1. activity_main 리사이클러뷰배치
 * 2. layout manager xml생성
 * 3. 어댑터 생성
 * 4. **/
public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    wordlistAdapter mAdapter;
    ArrayList<String> mwordlist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populatewords();
        mRecyclerView=findViewById(R.id.recycler);
        mAdapter=new wordlistAdapter(this,mwordlist);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    String word[]={"word1","word2","word3","word4","word5","word6","word7","word8","word9","word10","word11","word12","word13","word14","word15","word16","word17","word18","word19","word20","word21","word22"};
    private void populatewords(){
        for(int i=1;i<word.length;i++){
            mwordlist.add(word[i]);
        }
    }
}