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

    public void openGame1(View view){
        Intent IntentGame1 = new Intent(this,Game1.class);
        startActivity(IntentGame1);
    }

    public void openGame2(View view){
        Intent IntentGame2 = new Intent(this,NewGame.class);
        startActivity(IntentGame2);
    }
}