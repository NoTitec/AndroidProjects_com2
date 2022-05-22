package com.example.mymusicpractice;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public ImageView img;
        public TextView colname;
        //private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtName);
            img = itemView.findViewById(R.id.imageView);
            colname = itemView.findViewById(R.id.txtArtistName);
            //itemLayout =  itemView.findViewById(R.id.itemLayout);
            //itemView.setOnClickListener(this);
            name.setOnClickListener(this);
            img.setOnClickListener(this);
            colname.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();//선택한 뷰혼더 넘버

            switch ( view.getId() ) {
                case R.id.txtName: // ss = "name";
                    // search method 1
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);//intent 타입 제공
                    intent.putExtra(SearchManager.QUERY, itemsList.get(mPosition).getName()); // query contains search string
                    // search method 2
                /*try {
                    String eQuery = URLEncoder.encode(itemsList.get(mPosition).getName(), "UTF-8");
                    Uri uri = Uri.parse("http://www.google.com/search?q=" + eQuery);
                    intent = new Intent(Intent.ACTION_VIEW, uri);//intent타입과 값제공
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                    context.startActivity(intent);
                    break;
                case R.id.txtArtistName:
                    Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);//intent 타입 제공
                    intent2.putExtra(SearchManager.QUERY, itemsList.get(mPosition).getColname());
                    context.startActivity(intent2);
                    break;
                case R.id.imageView: //) ss = "image ";
                    Intent sintent=new Intent(context,ImageActivity.class);//메인 activity context를 imageactivity로 보내겠다(main과 어댑터는 같은 context다)
                    sintent.putExtra("image", itemsList.get(mPosition).getImage());//intent객체에 전달한 자료
                    sintent.putExtra("mydata", "if you need more");

                    context.startActivity(sintent);//제어를 ImageActivity로 넘김
                    break;
            }
            /*Toast.makeText(context,"Position = "+mPosition+"\n Item = "
                    +itemsList.get(mPosition).getName(),Toast.LENGTH_SHORT).show();*/

        }
    }
}
