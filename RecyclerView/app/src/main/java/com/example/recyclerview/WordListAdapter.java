package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>//1.어댑터가 사용할 자료가뭔지 지정
{
    private LayoutInflater mInflater;
    private final ArrayList<String> mWordList;

    public WordListAdapter(Context context,
                           ArrayList<String> wordList) {
        mInflater = LayoutInflater.from(context);//Linearlayout을 뷰로 만들어줌 ?????
        this.mWordList = wordList;
    }


    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//2.한개가 어떻게 보여질지 정보 가지는 클래스, 리스너도 여기에 넣어야함
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {//아이템뷰와 아이템객체를 인자로 View는 보여줄 한줄의 뷰
            super(itemView);//부모 정보 초기화
            wordItemView = itemView.findViewById(R.id.word);
            //
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int mPosition=getLayoutPosition();
            String element=mWordList.get(mPosition);
            mWordList.set(mPosition,"click"+element);
            mAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//oncreate와 onbind는 개수만큼 자동호출됨
        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);//1개뷰객체가짐
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);




    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}