package com.stegano.myjavaapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stegano.myjavaapplication.R;
import com.stegano.myjavaapplication.dto.RecyclerViewItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private ArrayList<RecyclerViewItem> mList;

    // Adapter를 생성시 리스트를 받아오고 mList에 넣어줌
    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> list) {
        this.mList = list;
    }

    // 어떤 레이아웃을 인플레이트해서 보여줄지 정해서 뷰홀더로 반환해줌
    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    // 실제 동작에 관한 기능을 구현
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.id.setText(mList.get(position).getId());
        holder.english.setText(mList.get(position).getEnglish());
        holder.korean.setText(mList.get(position).getKorean());
    }

    @Override
    public int getItemCount() {
        return (null != mList) ? mList.size() : 0;
    }


    // 뷰 홀더를 상속받아 구현해주어야 함
    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView english;
        protected TextView korean;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.id = itemView.findViewById(R.id.idListItem);
            this.english = itemView.findViewById(R.id.englishListItem);
            this.korean = itemView.findViewById(R.id.koreanListItem);
        }
    }
}
