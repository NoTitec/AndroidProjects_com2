package com.example.recyclerimage_text;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.MyViewHolder> {



private List<Items> itemsList;

        Context mContext;



        WordListAdapter(Context cxt, List<Items> mItemList){

        mContext = cxt;

        this.itemsList = mItemList;

        }



@Override

public WordListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.wordlist_item,parent,false);

        return new MyViewHolder(view);

        }



@Override

public void onBindViewHolder(WordListAdapter.MyViewHolder holder, int position) {



final Items item = itemsList.get(position);

        //convert resource-ID to drawble image

        Drawable d = mContext.getDrawable(item.getImgID());

        holder.img.setImageDrawable(d);

        holder.name.setText(item.getTitleStr()); //
        holder.kname.setText(item.getKtitleStr());
        }



@Override

public int getItemCount() {

        return itemsList.size();

        }



class MyViewHolder extends RecyclerView.ViewHolder

        implements View.OnClickListener {



    public TextView name;
    public TextView kname;
    public ImageView img;



    public MyViewHolder(View itemView) {

        super(itemView);

        name = itemView.findViewById(R.id.txtName);
        kname=itemView.findViewById(R.id.txtKName);
        img = itemView.findViewById(R.id.imgName);

        //itemView.setOnClickListener(this); 아이템 전체에 대한 리스너

        name.setOnClickListener(this);
        kname.setOnClickListener(this);
        img.setOnClickListener(this);

    }



    @Override

    public void onClick(View view) {

        String ss="";

        if( view.getId() == R.id.txtName) ss = "name";
        if( view.getId()==R.id.txtKName) ss="kname";
        if( view.getId() == R.id.imgName) ss = "image ";

        // Get the position of the item that was clicked.

        int mPosition = getLayoutPosition();

        Toast.makeText(mContext,ss + " Position = "+mPosition+"\n Item = "

                +itemsList.get(mPosition).getTitleStr(),Toast.LENGTH_LONG).show();

    }

}

}

