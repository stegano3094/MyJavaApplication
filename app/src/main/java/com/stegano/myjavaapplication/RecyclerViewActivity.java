package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stegano.myjavaapplication.adapters.RecyclerView2Item;
import com.stegano.myjavaapplication.adapters.RecyclerViewAdapter;
import com.stegano.myjavaapplication.adapters.RecyclerViewItem;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";

    RecyclerView recyclerView;
    Button addItemBtn;
    Button deleteItemBtn;

    private ArrayList<RecyclerViewItem> arrayList;
    private ArrayList<RecyclerView2Item> subArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int count = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        // 리사이클러뷰
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        subArrayList = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(arrayList);
//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(recyclerViewAdapter);

        // "데이터 추가하기" 클릭 시 데이터 추가 후 갱신
        addItemBtn = (Button) findViewById(R.id.addItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                subArrayList.clear();
                for (int i = 0; i < (int)(Math.random() * 10 + 1); i++) {
                    subArrayList.add(new RecyclerView2Item("123", "" + (int)(Math.random() * 10 + 1)));
                }

                // count%2 -> 0, 1이 번갈아 나오게 하기 위해서
                RecyclerViewItem item = new RecyclerViewItem(0, count+"", "Apple" + count, "사과" + count, false, subArrayList);
                arrayList.add(item);  // 마지막에 삽입
//                arrayList.add(2, item);  // 중간에 삽입


                recyclerView.scrollToPosition(arrayList.size()-1);  // 맨 아래로 스크롤
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        deleteItemBtn = (Button) findViewById(R.id.deleteItemBtn);
        deleteItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "---------------------------");
                for (int i = 0; i < arrayList.size(); i++) {
                    Log.d(TAG, "getId : " + arrayList.get(i).getId());
                    Log.d(TAG, "getKorean : " + arrayList.get(i).getKorean());
                    Log.d(TAG, "getEnglish : " + arrayList.get(i).getEnglish());
                    Log.d(TAG, "getIsChecked : " + arrayList.get(i).getIsChecked());

                    if (arrayList.get(i).getIsChecked() == true) {
                        Log.d(TAG, "arrayList.remove(" + i + ")");
                        arrayList.remove(i);
                        i--;
                    }
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        String a = "dkalsfjdsklf";

        testing(a);

        Log.d(TAG, "a : "+ a);
    }

    public void testing(String a) {
        a = "dfneaklfnsagk";
    }
}