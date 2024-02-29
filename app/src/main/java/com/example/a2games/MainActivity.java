package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView saludo;
    private SharedPreferences sharedPreferences;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saludo = findViewById(R.id.saludo);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("username", "");
        saludo.setText("Hola "+savedName);
    }

    public void openGame1(View v) {
        Intent intent = new Intent(this, Game1.class);
        startActivity(intent);
    }

    public void openSenku(View view){
        Intent IntentSenku = new Intent(this,Senku.class);
        startActivity(IntentSenku);
    }

    public void openProfile(View view){
        Intent intentUserProfile = new Intent(this,UserProfile.class);
        startActivity(intentUserProfile);
    }

}