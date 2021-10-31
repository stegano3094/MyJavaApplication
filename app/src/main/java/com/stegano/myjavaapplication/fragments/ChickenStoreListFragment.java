package com.stegano.myjavaapplication.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.stegano.myjavaapplication.R;

public class ChickenStoreListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chicken_store_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 동작에 관련된 코드

        TextView chickenStoreListTxt = getView().findViewById(R.id.chickenStoreListTxt);
        chickenStoreListTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v, "치킨 가게입니다", Snackbar.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("안내")
                        .setMessage("종료하시겠습니까?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(requireContext(), "예 누름", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(requireContext(), "아니오 누름", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}
