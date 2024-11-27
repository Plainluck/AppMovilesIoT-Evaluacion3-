package com.example.clase_30_09.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;

import com.example.clase_30_09.R;
import com.example.clase_30_09.dtos.datos_de_usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    EditText inputNombre, inputApellido, inputCargo, inputSueldo;
    Button updateBtn, deleteBtn;
    DatabaseReference databaseReference;
    datos_de_usuario userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        inputNombre = findViewById(R.id.inputNombre);
        inputApellido = findViewById(R.id.inputApellido);
        inputCargo = findViewById(R.id.inputCargo);
        inputSueldo = findViewById(R.id.inputSueldo);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        userData = (datos_de_usuario) getIntent().getSerializableExtra("userData");

        inputNombre.setText(userData.getNombre());
        inputApellido.setText(userData.getApellido());
        inputCargo.setText(userData.getCargo());
        inputSueldo.setText(String.valueOf(userData.getSueldo()));

        databaseReference = FirebaseDatabase.getInstance().getReference("datos_de_usuario");
        updateBtn.setOnClickListener(v -> dataUpdate());
        deleteBtn.setOnClickListener(v -> deleteData());
    }
    private void dataUpdate() {
        String nombre = inputNombre.getText().toString().trim();
        String apellido = inputApellido.getText().toString().trim();
        String cargo = inputCargo.getText().toString().trim();
        int sueldo = Integer.parseInt(inputSueldo.getText().toString().trim());

        if (nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Por favor completa los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        userData.setNombre(nombre);
        userData.setApellido(apellido);
        userData.setCargo(cargo);
        userData.setSueldo(sueldo);

        databaseReference.child(userData.getId()).setValue(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show());
    }


    private void deleteData() {
        databaseReference.child(userData.getId()).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show());
    }
}