package com.example.middle2_test;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder> {

    private List<Item> itemsList;
    Context context;

    RecyclerviewItemAdapter(List<Item> mItemList, Context context){
        this.context = context;
        this.itemsList = mItemList;
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    //리사이클러뷰 1개의 뷰를 정의하는 클래스
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public ImageView img;

        public MyViewHolder(View itemView) {//리사이클러뷰 어댑터가 inflate한 view 객체 전달해줌
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.imageView);

            name.setOnClickListener(this);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();//선택한 뷰혼더 넘버

            switch ( view.getId() ) {
                case R.id.txtName:
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);//intent 타입 제공
                    intent.putExtra(SearchManager.QUERY, itemsList.get(mPosition).getName()); // query contains search string
                    context.startActivity(intent);
                    break;
                case R.id.imageView:
                    Intent sintent=new Intent(context,ImageActivity.class);//메인 activity context를 imageactivity로 보내겠다(main과 어댑터는 같은 context다)
                    sintent.putExtra("image", itemsList.get(mPosition).getBig());//intent객체에 전달한 자료
                    sintent.putExtra("mydata", "if you need more");

                    context.startActivity(sintent);//제어를 ImageActivity로 넘김
                    //내가 manifests설정안하고 삽질함 ㅅㅂ 주의하자
                    break;
            }

        }
    }


    @Override// onCreateViewHolder가 던져준 1개 MyViewHolder에 실제 데이터 연결
    public void onBindViewHolder( MyViewHolder holder, int position) {
        final Item item = itemsList.get(position);
        holder.name.setText(item.getName());
        Drawable d=context.getDrawable(item.getSmall());
        holder.img.setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


}
