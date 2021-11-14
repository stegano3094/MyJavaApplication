package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stegano.myjavaapplication.adapters.RecyclerViewAdapter;
import com.stegano.myjavaapplication.dto.RecyclerViewItem;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";

    RecyclerView recyclerView;
    Button addItemBtn;
    Button deleteItemBtn;

    private ArrayList<RecyclerViewItem> arrayList;
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

                RecyclerViewItem item = new RecyclerViewItem(count+"", "Apple" + count, "사과" + count, false);
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
    }

}