package com.stegano.myjavaapplication.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stegano.myjavaapplication.R;
import com.stegano.myjavaapplication.dto.RecyclerViewItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<RecyclerViewItem> mList;

    // Adapter를 생성시 리스트를 받아오고 mList에 넣어줌
    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> list) {
        this.mList = list;
    }

    // 어떤 레이아웃을 인플레이트해서 보여줄지 정해서 뷰홀더로 반환해줌
    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    // 실제 동작에 관한 기능을 구현 (onCreateViewHolder() -> onBindViewHolder()가 호출됨)
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() position : " + position);

        holder.id.setText(mList.get(position).getId());
        holder.english.setText(mList.get(position).getEnglish());
        holder.korean.setText(mList.get(position).getKorean());

        /**
         * 홀더를 재사용하기 때문에 홀더에 리스너를 달면 다른 리스트에도 영향을 준다.
         * 그래서 홀더를 재사용할 때에는 다른 리스트에 영향이 가지 않도록 리스너를 초기화해야 한다.
         *
         */
        holder.checkBox.setOnCheckedChangeListener(null);  // 리스너를 초기화한 뒤 리스너를 다시 연결시켜주면 체크박스가 정상 동작함..
        holder.checkBox.setChecked(mList.get(position).getIsChecked());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(position).setIsChecked(isChecked);

                holder.checkBox.setChecked(mList.get(position).getIsChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mList) ? mList.size() : 0;
    }


    // 뷰 홀더를 상속받아 구현해주어야 함
    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout cardItem;
        protected TextView id;
        protected TextView english;
        protected TextView korean;
        protected CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.cardItem = itemView.findViewById(R.id.cardItem);
            this.id = itemView.findViewById(R.id.idListItem);
            this.english = itemView.findViewById(R.id.englishListItem);
            this.korean = itemView.findViewById(R.id.koreanListItem);
            this.checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
