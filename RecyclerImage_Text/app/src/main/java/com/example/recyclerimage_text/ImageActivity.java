package com.example.recyclerimage_text;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_main);

        ImageView imgView= findViewById(R.id.imgView);
        //prepare for receiving intent from adapter
        Intent intent=new Intent(this.getIntent());//누군가 ImageActivity class에 전달한 intent가 있는지 확인하고 가져옴
        int s = intent.getIntExtra("image", 0);//값이없을경우 default는 0으로
        Drawable dr = getDrawable(s);
        imgView.setImageDrawable(dr);
        //this data is just for test~
        String as = intent.getStringExtra("mydata");
        Log.e("***", "Received data is " + as); //check data
    }
}
