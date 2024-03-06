package com.example.a2games;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    private SharedPreferences sharedPreferences;

    private Button buttonLogin;

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
                // Si coinciden, iniciar sesión y abrir MainActivity con transición
                Intent intent = new Intent(this, MainActivity.class);
                clickAnimation();
                startActivityWithTransition(intent);
                finish();
            } else {
                // Si no coinciden, mostrar un mensaje de error o tomar la acción adecuada
                // Por ejemplo, puedes mostrar un Toast indicando que las credenciales son incorrectas
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityWithTransition(Intent intent) {
        // Configurar la transición de actividad personalizada
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out);
        startActivity(intent, options.toBundle());
    }

    private void clickAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonLogin.startAnimation(animation); // Aplicar animación al botón de inicio de sesión
        // Puedes aplicar la animación a cualquier otra vista que desees animar
    }

    public void openRegistro(View view) {
        Intent intentRegistro = new Intent(this, RegisterActivity.class);
        // Configurar la transición de actividad personalizada
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intentRegistro, options.toBundle());
    }
}
