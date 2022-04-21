package com.example.recyclerpractice;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//리사이클러뷰어댑터는 RecyclerView.Adapter상속받아야하며 다시 이는 제네릭으로 ViewHolder를 필요로함 리사이클러 기본뷰홀더있지만
//여기서는 innerclass로 직접 뷰홀더를 정의해서 전달함 innerclass정의했으므로 <>내부에는 현재 클래스명.정의한 innerclass이름
public class RAdapter extends RecyclerView.Adapter<RAdapter.RAdapterViewHolder> {
    ArrayList<Stringitem> mwordlist;//어댑터가 사용할 데이터
    public RAdapter(Context context, ArrayList<Stringitem> wordlist) {//생성자로 메인 context와 사용할 데이터 받아옴
        this.mwordlist=wordlist;
    }
    //1.
    @Override
    public int getItemCount() {//리사이클러뷰가 보여줄 개수
        return mwordlist.size();
    }
    //4.
    @Override//전달받은 뷰홀더에 실제 내용을 채움
    public void onBindViewHolder(@NonNull RAdapterViewHolder holder, int position) {
        //CreateViewHolder로 만든 걸 재활용하며 내용만 바꿈
        Log.e("!","bind");
        String cur=mwordlist.get(position).getOneitem();
        holder.titemView.setText(cur);
    }
    //3.
    @NonNull
    @Override//뷰홀더를 만들어서 bindviewholder에 전달
    public RAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어댑터가 context받으면 잘못짠 코드
        //보여줄 개수만큼만호출함 //인자의 parent가 리사이클러뷰자체임
        Log.e("?","create");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);//attach는 항상 false
        //어댑터홀더에 inflate뷰(2개있는데 아래 걸 써야함 위쪽건 mainactivity만가능)와
        return new RAdapterViewHolder(view);
    }
    //2.
    class RAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{//어댑터가 가지는 뷰홀더 여기서 뷰하나보여줄것 정의하고 이벤트리스너등을 등록한다
        //바인딩한 뷰를 홀드하고있음
        TextView titemView;//item_view가가진 컨트롤
        //RAdapter mAdapter;//이번트처리시 어댑터에 변경알리기위한 어댑터전달받기 단순 출력시엔 필요없다

        public RAdapterViewHolder(@NonNull View itemView) {//oncreate메소드에서 inflate한 뷰를 전달받음
            super(itemView);
            titemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
            //this.mAdapter = mAdapter;
        }

        @Override
        public void onClick(View view) {
            int sel=getLayoutPosition();//현재 뷰홀더가 선택되면 선택된 위치반환
            String selstring=mwordlist.get(sel).getOneitem();
            mwordlist.get(sel).setOneitem("click");
            RAdapter.this.notifyDataSetChanged();
        }
    }
}
