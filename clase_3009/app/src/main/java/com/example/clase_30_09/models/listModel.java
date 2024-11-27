package com.example.clase_30_09.models;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.clase_30_09.dtos.datos_de_usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listModel {

    private final DatabaseReference databaseReference;
    private final Context context;

    public listModel(Context context) {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("datos_de_usuario");
        this.context = context;
    }

    public interface DataStatus {
        void DataIsLoaded(List<datos_de_usuario> users);
        void onError();
    }

    public void fetchUsers(final DataStatus dataStatus) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<datos_de_usuario> userList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    datos_de_usuario user = dataSnapshot.getValue(datos_de_usuario.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                dataStatus.DataIsLoaded(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                dataStatus.onError();
            }
        });
    }
}
