package com.example.a2games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Game1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_layout);
    }

    public void goBack(View view){
        Intent goBack = new Intent(this,MainActivity.class);
        startActivity(goBack);
    }
}