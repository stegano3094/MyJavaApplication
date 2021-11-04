package com.stegano.myjavaapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.stegano.myjavaapplication.R;
import com.stegano.myjavaapplication.datas.Store;

import java.util.List;

public class PizzaStoreAdapter extends ArrayAdapter<Store> {
    LayoutInflater inflater;
    Context context;
    int resource;
    List<Store> objects;

    public PizzaStoreAdapter(@NonNull Context context, int resource, @NonNull List<Store> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View tempRow = convertView;
        if(tempRow == null) {
            tempRow = inflater.inflate(R.layout.pizza_store_list_item, null);
        }

        Store data = objects.get(position);
        ImageView pizzaStoreLogoImg = (ImageView) tempRow.findViewById(R.id.pizzaStoreLogoImg);
        TextView pizzaStoreNameTxt = tempRow.findViewById(R.id.pizzaStoreNameTxt);

        pizzaStoreNameTxt.setText(data.getName());
        Glide.with(context)
                .load(data.getLogoUrl())
                .error(R.drawable.logo_main)
                .into(pizzaStoreLogoImg);
        // Glide와 Picasso는 비슷하게 사용가능함
//        Picasso.get()
//                .load(data.getLogoUrl())
//                .error(R.drawable.logo_main)
//                .into(pizzaStoreLogoImg);

        return tempRow;
    }
}
