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

//        String ss="";
//
//        if( view.getId() == R.id.txtName) ss = "name";
//        if( view.getId()==R.id.txtKName) ss="kname";
//        if( view.getId() == R.id.imgName) ss = "image ";

        // Get the position of the item that was clicked.

        int mPosition = getLayoutPosition();

        switch ( view.getId() ) {
            case R.id.txtName: case R.id.txtKName: // ss = "name";
                // search method 1
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);//
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

