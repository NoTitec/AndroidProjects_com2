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
import java.net.URLEncoder;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.MyViewHolder> {

    private List<SingleItem> itemsList;
    Context mContext;

    WordListAdapter(Context cxt, List<SingleItem> mItemList){
        mContext = cxt;
        this.itemsList = mItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final SingleItem item = itemsList.get(position);

        holder.name.setText(item.getTitle()); //
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView name;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtNews);

            //itemView.setOnClickListener(this); ?꾩씠???꾩껜?????由ъ뒪??
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
                Uri uri = Uri.parse(itemsList.get(mPosition).getLink());
                itemsList.get(mPosition).getLink();
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mContext.startActivity(intent);
            //Toast.makeText(mContext,ss + " Position = "+mPosition+"\n Item = "
            //        +itemsList.get(mPosition).getTitle(),Toast.LENGTH_SHORT).show();
        }
    }
}

