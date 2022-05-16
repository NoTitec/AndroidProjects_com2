package com.example.recyclerimage_text;

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



private List<Items> itemsList;

        Context mContext;



        WordListAdapter(Context cxt, List<Items> mItemList){

        mContext = cxt;

        this.itemsList = mItemList;

        }



@Override

public WordListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//parent는 리사이클러뷰

        View view = LayoutInflater.from(mContext).inflate(R.layout.wordlist_item,parent,false);// 메인 context의 어댑터 레이아웃을 리사이클러뷰를위해 inflat함

        return new MyViewHolder(view);//1개의 새로운 뷰홀더 리턴

        }



@Override

public void onBindViewHolder(WordListAdapter.MyViewHolder holder, int position) {//oncreateViewholder로 만든 1개 뷰홀더에 데이터를 bind



final Items item = itemsList.get(position);// item리스트 번호 가져옴

        //convert resource-ID to drawble image

        Drawable d = mContext.getDrawable(item.getImgID());//이미지는 Drawble 에 이미지 id 넣어서 객체로 만듬

        holder.img.setImageDrawable(d);

        holder.name.setText(item.getTitleStr()); //
        holder.kname.setText(item.getKtitleStr());
        }



@Override

public int getItemCount() {

        return itemsList.size();

        }



class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {//리사이클러뷰의 뷰홀더 inner클래스

    public TextView name;
    public TextView kname;
    public ImageView img;

    public MyViewHolder(View itemView) {// 뷰홀더 생성자

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

//        String ss="";
//
//        if( view.getId() == R.id.txtName) ss = "name";
//        if( view.getId()==R.id.txtKName) ss="kname";
//        if( view.getId() == R.id.imgName) ss = "image ";

        // Get the position of the item that was clicked.

        int mPosition = getLayoutPosition();//선택한 뷰혼더 넘버

        switch ( view.getId() ) {
            case R.id.txtName: case R.id.txtKName: // ss = "name";
                // search method 1
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);//intent 타입 제공
                intent.putExtra(SearchManager.QUERY, itemsList.get(mPosition).getTitleStr()); // query contains search string
                // search method 2
//                try {
//                    String eQuery = URLEncoder.encode(itemsList.get(mPosition).getTitleStr(), "UTF-8");
//                    Uri uri = Uri.parse("http://www.google.com/search?q=" + eQuery);
//                    intent = new Intent(Intent.ACTION_VIEW, uri);//intent타입과 값제공
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                mContext.startActivity(intent);
                break;

            case R.id.imgName: //) ss = "image ";
                Intent sintent=new Intent(mContext,ImageActivity.class);//메인 activity context를 imageactivity로 보내겠다(main과 어댑터는 같은 context다)
                sintent.putExtra("image", itemsList.get(mPosition).getImgID());//intent객체에 전달한 자료
                sintent.putExtra("mydata", "if you need more");

                mContext.startActivity(sintent);//제어를 ImageActivity로 넘김
                break;
        }
//        Toast.makeText(mContext,ss + " Position = "+mPosition+"\n Item = "
//
//                +itemsList.get(mPosition).getTitleStr(),Toast.LENGTH_SHORT).show();

    }

}

}

