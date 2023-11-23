package com.example.a2games;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game1 extends AppCompatActivity {
    private static final int NUM_BUTTONS = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_layout);

        Random random = new Random();
        int randomButton1 = random.nextInt(NUM_BUTTONS) + 1;
        int randomButton2 = random.nextInt(NUM_BUTTONS) + 1;

        while (randomButton2 == randomButton1) {
            randomButton2 = random.nextInt(NUM_BUTTONS) + 1;
        }

        Button[] buttons = new Button[NUM_BUTTONS];
        for (int i = 0; i < NUM_BUTTONS; i++) {
            String buttonId = "button" + (i + 1);
            int buttonResId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = findViewById(buttonResId);
        }

        buttons[randomButton1 - 1].setVisibility(View.VISIBLE);
        buttons[randomButton2 - 1].setVisibility(View.VISIBLE);

        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
        });
    }

    public void goBack(View view) {
        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);
    }
}