package com.example.recyclerpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
//itemView 내부 Linear 뷰의 높이설정 주의!! parent면 제대로 안뜬다!
public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RAdapter radapter;
    ArrayList<Stringitem> stringitems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=1;i<100;i++){
            stringitems.add(new Stringitem(Integer.toString(i)));
        }



        mRecyclerView=findViewById(R.id.recyclerview);
        radapter = new RAdapter(this, stringitems);
        mRecyclerView.setAdapter(radapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}