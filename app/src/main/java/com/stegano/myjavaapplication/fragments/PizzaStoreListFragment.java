package com.stegano.myjavaapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stegano.myjavaapplication.R;
import com.stegano.myjavaapplication.ViewStoreDetailActivity;
import com.stegano.myjavaapplication.adapters.PizzaStoreAdapter;
import com.stegano.myjavaapplication.datas.Store;

import java.util.ArrayList;

public class PizzaStoreListFragment extends Fragment {
    ArrayList<Store> mPizzaStoreDataList;
    PizzaStoreAdapter mPizzaStoreAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pizza_store_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 동작에 관련된 코드
        mPizzaStoreDataList = new ArrayList<Store>();
        mPizzaStoreDataList.add( new Store("mm1", "1111-1111", "https://cdn3.iconfinder.com/data/icons/street-food-and-food-trucker-1/64/pizza-fast-food-bake-bread-128.png"));
        mPizzaStoreDataList.add( new Store("mm2", "2222-2222", "https://cdn1.iconfinder.com/data/icons/cartoon-snack/128/pizza-128.png"));
        mPizzaStoreDataList.add( new Store("mm3", "3333-3333", "https://cdn3.iconfinder.com/data/icons/food-set-3/91/Food_C219-128.png"));
        mPizzaStoreDataList.add( new Store("mm4", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm5", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm6", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm7", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm7", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm7", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm7", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));
        mPizzaStoreDataList.add( new Store("mm7", "4444-4444", "https://cdn0.iconfinder.com/data/icons/fastfood-31/64/pizza-italian-food-fast-fastfood-cheese-piece-128.png"));

        // Context context, int resource, @NonNull List<Store> objects
        mPizzaStoreAdapter = new PizzaStoreAdapter(requireContext(), R.layout.pizza_store_list_item, mPizzaStoreDataList);

        ListView pizzaStoreListView = getView().findViewById(R.id.pizzaStoreListView);
        pizzaStoreListView.setAdapter(mPizzaStoreAdapter);


        pizzaStoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store clickedStore = mPizzaStoreDataList.get(position);
                Intent myIntent = new Intent(requireContext(), ViewStoreDetailActivity.class);
                myIntent.putExtra("storeData", clickedStore);
                startActivity(myIntent);
            }
        });
    }
}
