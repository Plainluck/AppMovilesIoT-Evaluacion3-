package com.example.clase_30_09.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.annotation.Nullable;
import androidx.core.view.WindowInsetsCompat;

import com.example.clase_30_09.R;

public class MainActivity extends AppCompatActivity {
    EditText unChars, upChars;
    Button loginBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, tabLayoutxd.class);
            startActivity(intent);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        unChars = findViewById(R.id.unChars);
        upChars = findViewById(R.id.upChars);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (validateInputs()){
                    authenticateUser(unChars.getText().toString(), upChars.getText().toString());
                }
            }
        });
    }

    private void authenticateUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, tabLayoutxd.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Error en la autenticación: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateInputs() {
        String email = unChars.getText().toString().trim();
        String password = upChars.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            unChars.setError("Por favor, ingresa un email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            unChars.setError("Ingresa un email válido");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            upChars.setError("Por favor, ingresa una contraseña");
            return false;
        }
        return true;
    }





}
