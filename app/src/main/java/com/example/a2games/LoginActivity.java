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

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        buttonLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            if (username.equals(savedUsername) && password.equals(savedPassword)) {
                Intent intent = new Intent(this, MainActivity.class);
                clickAnimation();
                startActivityWithTransition(intent);
                finish();
            } else {
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startActivityWithTransition(Intent intent) {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slide_in, R.anim.slide_out);
        startActivity(intent, options.toBundle());
    }

    private void clickAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        buttonLogin.startAnimation(animation);
    }

    public void openRegistro(View view) {
        Intent intentRegistro = new Intent(this, RegisterActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intentRegistro, options.toBundle());
    }
}
