package com.example.a2games;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    private EditText editTextPasswordConfirm;

    private int bestScore2048;

    private int bestScoreSenku;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        Button buttonRegister = findViewById(R.id.buttonRegister);



        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);


        buttonRegister.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String passwordConfirm = editTextPasswordConfirm.getText().toString();
            if(password.equals(passwordConfirm)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.putInt("score2048", (bestScore2048));
                editor.putInt("scoreSenku",(bestScoreSenku));
                editor.putString("image_uri",null);
                editor.apply();

                openLogin(null);
            }else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

            }


        });
    }
    public void openLogin(View view){
        Intent intentLogin = new Intent(this, LoginActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        startActivity(intentLogin, options.toBundle());
        finish();
    }
}
