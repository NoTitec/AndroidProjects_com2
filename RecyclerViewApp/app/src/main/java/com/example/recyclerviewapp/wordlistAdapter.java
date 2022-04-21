package com.example.recyclerviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class wordlistAdapter extends RecyclerView.Adapter<wordlistAdapter.wordViewHolder>  {
    private LayoutInflater mInflater;//xml을 실제보이게하는 클래스
    private final ArrayList<String> mWordList;

    public wordlistAdapter(Context context, ArrayList<String> mWordList) {//생성자
        mInflater = LayoutInflater.from(context);
        this.mWordList=mWordList;
    }

    class wordViewHolder extends RecyclerView.ViewHolder{
        public final TextView wordItemView;
        final wordlistAdapter mAdapter;

        public wordViewHolder(@NonNull View itemView,wordlistAdapter adapter) {//worldlist_item뷰 안의 텍스트 객체찾아야함
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }
    @NonNull
    @Override
    public wordlistAdapter.wordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//보이는것,보이는게 필요할때마다 자동호출
        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);
        return new wordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull wordlistAdapter.wordViewHolder holder, int position) {//viewholder와 데이터가 연결되는 순간 호출
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {//view에보이는 개수
        return mWordList.size();
    }//spinersk listview와 다르게 본인만의 고유 어댑터가 필요하므로 별도 클래스정의필요 이클래스의 wordViewHolder를 쓰겠다는뜻

}
