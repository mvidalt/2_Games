package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView nombre;
    private TextView scoreSenku;
    private TextView score2048;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nombre = findViewById(R.id.userName);
        score2048 = findViewById(R.id.score2048Value);
        scoreSenku = findViewById(R.id.scoreSenkuValue);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String savedName = sharedPreferences.getString("username", "");
        int savedScore2048 = sharedPreferences.getInt("score2048", 0);
        int savedScoreSenku = sharedPreferences.getInt("scoreSenku", 0);
        nombre.setText(savedName);
        score2048.setText(String.valueOf(savedScore2048));
        scoreSenku.setText(String.valueOf(savedScoreSenku));
    }

    public void goMainMenu(View view){
        startActivity( new Intent(this,MainActivity.class));
    }
}