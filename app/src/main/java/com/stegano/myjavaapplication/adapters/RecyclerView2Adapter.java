package com.stegano.myjavaapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stegano.myjavaapplication.R;

import java.util.ArrayList;

public class RecyclerView2Adapter extends RecyclerView.Adapter<RecyclerView2Adapter.MyViewHolder2> {
    private static final String TAG = "RecyclerViewAdapter2";

    private ArrayList<RecyclerView2Item> mList2;
    private Context mContext;

    public RecyclerView2Adapter(ArrayList<RecyclerView2Item> list) {
        this.mList2 = list;
    }

    @NonNull
    @Override
    public RecyclerView2Adapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_2_item, parent, false);
        MyViewHolder2 myViewHolder2 = new MyViewHolder2(view);

        return myViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView2Adapter.MyViewHolder2 holder, int position) {
        holder.userName.setText(mList2.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList2.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        ImageView userImage;
        TextView userName;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
        }
    }
}
