package com.example.a2games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2games.MainActivity;
import com.example.a2games.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Inicializar vistas
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Obtener SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Acción al hacer clic en el botón de inicio de sesión
        buttonLogin.setOnClickListener(view -> {
            // Obtener los valores ingresados
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            // Obtener los valores guardados en SharedPreferences
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            // Verificar si los valores ingresados coinciden con los guardados
            if (username.equals(savedUsername) && password.equals(savedPassword)) {
                // Si coinciden, iniciar sesión y abrir MainActivity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Si no coinciden, mostrar un mensaje de error o tomar la acción adecuada
                // Por ejemplo, puedes mostrar un Toast indicando que las credenciales son incorrectas
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openRegistro(View view){
        Intent IntentRegistro = new Intent(this, RegisterActivity.class);
        startActivity(IntentRegistro);
    }
}
