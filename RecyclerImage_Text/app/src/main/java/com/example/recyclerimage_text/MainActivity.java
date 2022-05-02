package com.example.recyclerimage_text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private WordListAdapter wordListAdapter;

    private List<Items> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        itemsList = new ArrayList<Items>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        wordListAdapter = new WordListAdapter(this, itemsList);

        //recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(layoutManager);

        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(wordListAdapter);


        prepareItems();

        wordListAdapter.notifyDataSetChanged();

    }


    //data


    String[][] flowerName = {

            {"begonias", "베고니아"},

            {"bellflower", "도라지꽃"},

            {"cosmos", "코스모스"},

            {"crocus", "크로커스"},

            {"dahlia", "다알리아"},


            {"hyacinth", "히야신스"},

            {"hydrangea", "수국"},

            {"lavender", "라벤더"},

            {"rose", "장미"},

            {"sunflowers", "해바라기"},


            {"tulip", "튜립"},

            {"daffodils", "수선화"},

            {"geraniums", "제라늄"},

            {"marigolds", "금잔화"},

            {"morning glory", "나팔꽃"},


            {"pansy", "팬지"},

            {"salvia", "사루비아"},

            {"snapdragons", "금어초"},

            {"zinnia", "백일홍"},

            {"campanula", "초롱꽃"},


            {"lotus", "연꽃"},

            {"lupinus", "루피너스"},

            {"aster", "과꽃"},

            {"petunia", "페튜니아"}

    };

    int flowerId[] = {

            R.drawable.begonias,

            R.drawable.bellflower,

            R.drawable.cosmos,

            R.drawable.crocus,

            R.drawable.dahlia,


            R.drawable.hyacinth,

            R.drawable.hydragea,

            R.drawable.lavender,

            R.drawable.rose,

            R.drawable.sunflowers,


            R.drawable.tulip,

            R.drawable.daffodils,

            R.drawable.geraniums,

            R.drawable.marigolds,

            R.drawable.morningglory,


            R.drawable.pansy,

            R.drawable.salvia,

            R.drawable.snapdragons,

            R.drawable.zinnia,

            R.drawable.campanula,


            R.drawable.lotus,

            R.drawable.lupinus,

            R.drawable.aster,

            R.drawable.petunia

    };


    private void prepareItems() {

        for (int i = 0; i < flowerName.length; i++) {

            Items items = new Items(flowerId[i], flowerName[i][0],flowerName[i][1]);

            itemsList.add(items);

        }
    }
}