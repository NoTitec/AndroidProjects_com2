package com.example.mymusicpractice;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);

        ImageView imgView= findViewById(R.id.imgView);
        //prepare for receiving intent from adapter
        Intent intent=new Intent(this.getIntent());//누군가 ImageActivity class에 전달한 intent가 있는지 확인하고 가져옴
        String s = intent.getStringExtra("image");//값이없을경우 default는 0으로
        Log.e("?????","receive image link is "+s);
        Picasso.get().load(s)//item의 http...string 참조 이미지를 로드함
                //.placeholder(R.drawable.ic_baseline_play_arrow_24)//내부 벡터이미지 하나 배치
                //.error(R.drawable.error)
                //.fit()
                //.centerCrop()
                .into(imgView); //resize(width,height). 뷰홀더 이미지뷰에 로드이미지 배치

        /*Drawable dr = getDrawable(s);
        imgView.setImageDrawable(dr);*/
        //this data is just for test~
        String as = intent.getStringExtra("mydata");
        Log.e("***", "Received data is " + as); //check data
    }
}
