package com.example.rssapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.MyViewHolder> {

    private List<SingleItem> itemsList;//메인에서 전달받을 실제 데이터
    Context mContext;

    WordListAdapter(Context cxt, List<SingleItem> mItemList){
        mContext = cxt;
        this.itemsList = mItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//뷰홀더레이아웃 inflat하고 뷰홀더 하나 생성해 반환
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SingleItem item = itemsList.get(position);

        holder.name.setText(item.getTitle()); //onCreateViewHolder가 생성한 1개 뷰홀더에 데이터 바인딩
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView name;


        public MyViewHolder(View itemView) {//oncreate에서 inflate한 레이아웃뷰 인자받은뒤 이뷰 구조 설정
            super(itemView);
            name = itemView.findViewById(R.id.txtNews);

            //itemView.setOnClickListener(this);
            name.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String ss="";
            Intent intent=null;
            //if( view.getId() == R.id.txtNews) ss = "name";
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            try {
                Uri uri = Uri.parse(itemsList.get(mPosition).getLink());//main에서 받은 Singleitem리스트에서 선택 item1개꺼내서 링크 반환한뒤 uri.parse로 URL 객체 전환
                //itemsList.get(mPosition).getLink();
                intent = new Intent(Intent.ACTION_VIEW, uri);//웹페이지 url연결
            } catch (Exception e) {
                e.printStackTrace();
            }
            mContext.startActivity(intent);//main Context에게 intent권한 실행하라고 알려줌
            //Toast.makeText(mContext,ss + " Position = "+mPosition+"\n Item = "
            //        +itemsList.get(mPosition).getTitle(),Toast.LENGTH_SHORT).show();
        }
    }
}

