package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openGame1(View v) {
        Intent intent = new Intent(this, Game1.class);
        startActivity(intent);
    }

    public void openSenku(View view){
        Intent IntentSenku = new Intent(this,Senku.class);
        startActivity(IntentSenku);
    }

}