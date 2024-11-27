package com.example.clase_30_09.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clase_30_09.R;
import com.example.clase_30_09.dtos.datos_de_usuario;
import com.example.clase_30_09.models.listModel;

import java.util.List;

public class fragment_op2_list extends Fragment {
    private LinearLayout lly;
    private listModel listModel;

    public fragment_op2_list() {
        super(R.layout.fragment_op2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_op2, container, false);
        lly = view.findViewById(R.id.LinearLy);
        listModel = new listModel(getContext());

        loadData();

        return view;
    }

    private void loadData() {
        listModel.fetchUsers(new listModel.DataStatus() {
            @Override
            public void DataIsLoaded(List<datos_de_usuario> users) {
                lly.removeAllViews();
                for (datos_de_usuario userData : users) {
                    addCard(userData);
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    private void addCard(datos_de_usuario userData) {
        View cardView = LayoutInflater.from(getContext()).inflate(R.layout.cardview_template, lly, false);

        TextView nombreTxt = cardView.findViewById(R.id.nombreTxt);
        TextView sueldoTxt = cardView.findViewById(R.id.sueldoTxt);
        Button detailBtn = cardView.findViewById(R.id.detailBtn);

        nombreTxt.setText(userData.getNombre());
        sueldoTxt.setText(String.valueOf(userData.getSueldo()));

        detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditActivity.class);
            intent.putExtra("userData", userData);
            startActivity(intent);
        });

        lly.addView(cardView);
    }
}
