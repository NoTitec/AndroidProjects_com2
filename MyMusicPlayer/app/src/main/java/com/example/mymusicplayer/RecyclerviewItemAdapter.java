package com.example.mymusicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder> {

    private List<ContactsItems> itemsList;
    ///private ClickListener clickListener;
    Context context;


    RecyclerviewItemAdapter(List<ContactsItems> mItemList, Context context){
        this.context = context;
        this.itemsList = mItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ContactsItems item = itemsList.get(position);

        //image can be read thru internet
        // we need another asynctaskloader, which is cumbersome
        // we use external library Picasso
        Picasso.get().load(item.getImage())//item의 http...string 참조 이미지를 로드함
                .placeholder(R.drawable.ic_baseline_play_arrow_24)//내부 벡터이미지 하나 배치
                //.error(R.drawable.error)
                //.fit()
                //.centerCrop()
                .into(holder.img); //resize(width,height). 뷰홀더 이미지뷰에 로드이미지 배치
//        Glide.with(context).load(item.getImage())
//                .centerCrop()
//                .placeholder(R.drawable.load)
//                .error(R.drawable.error)
//                .into(holder.img);
        //holder.img.setImageDrawable(item.getImage().);

        holder.name.setText(item.getName()); //
        holder.colname.setText(item.getColname()); //
//

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView name;
        public ImageView img;
        public TextView colname;
        //private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.imgName);
            colname = itemView.findViewById(R.id.txtColname);
            //itemLayout =  itemView.findViewById(R.id.itemLayout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Toast.makeText(context,"Position = "+mPosition+"\n Item = "
                    +itemsList.get(mPosition).getName(),Toast.LENGTH_SHORT).show();

        }
    }
}