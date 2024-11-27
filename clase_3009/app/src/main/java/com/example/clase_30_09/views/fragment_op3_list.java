package com.example.clase_30_09.views;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.clase_30_09.R;
import com.example.clase_30_09.dtos.datos_de_usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class fragment_op3_list extends Fragment {
    private EditText inputTextNombre, inputTextApellido, inputTextCargo, inputTextSueldo;
    private Button submitFormBtn;
    private DatabaseReference databaseRef;

    public fragment_op3_list(){
        super(R.layout.fragment_op3);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_op3, container, false);

        databaseRef = FirebaseDatabase.getInstance().getReference("datos_de_usuario");

        inputTextNombre = view.findViewById(R.id.inputTextNombre);
        inputTextApellido = view.findViewById(R.id.inputTextApellido);
        inputTextCargo = view.findViewById(R.id.inputTextCargo);
        inputTextSueldo = view.findViewById(R.id.inputTextSueldo);
        submitFormBtn = view.findViewById(R.id.submitFormBtn);

        submitFormBtn.setOnClickListener(v -> {
            if (validateInputs()){
                sendtoFB();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        String nombre = inputTextNombre.getText().toString().trim();
        String apellido = inputTextApellido.getText().toString().trim();
        String cargo = inputTextCargo.getText().toString().trim();
        String sueldo = inputTextSueldo.getText().toString().trim();

        if (TextUtils.isEmpty(nombre)) {
            inputTextNombre.setError("Ingresa un nombre");
            return false;
        }

        if (TextUtils.isEmpty(apellido)) {
            inputTextApellido.setError("Ingresa un apellido");
            return false;
        }

        if (TextUtils.isEmpty(cargo)) {
            inputTextCargo.setError("Ingresa un cargo");
            return false;
        }

        if (TextUtils.isEmpty(sueldo) || !TextUtils.isDigitsOnly(sueldo)) {
            inputTextSueldo.setError("Ingresa un sueldo");
            return false;
        }
        return true;
    }

    private void sendtoFB() {
        String nombre = inputTextNombre.getText().toString().trim();
        String apellido = inputTextApellido.getText().toString().trim();
        String cargo = inputTextCargo.getText().toString().trim();
        int sueldo;

        try {
            sueldo = Integer.parseInt(inputTextSueldo.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "El sueldo debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }


        String id = databaseRef.push().getKey();

        datos_de_usuario datosDeUsuario = new datos_de_usuario(nombre, apellido, cargo, sueldo, id);

        databaseRef.child(id).setValue(datosDeUsuario).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                gotoFrag_op2();
            } else {
                Toast.makeText(getActivity(), "No se han podido registrar tus datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void gotoFrag_op2() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new fragment_op2_list())
                .addToBackStack(null)
                .commit();
    }
}
