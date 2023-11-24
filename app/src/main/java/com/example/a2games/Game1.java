package com.example.a2games;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game1 extends AppCompatActivity implements GestureDetector.OnGestureListener {
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

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {
        float diffY = motionEvent2.getY() - motionEvent1.getY();
        float diffX = motionEvent2.getX() - motionEvent1.getX();

        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                if (diffX > 0) {
                    // Deslizamiento hacia la derecha
                    Log.d("Mensaje","derecha");
                } else {
                    // Deslizamiento hacia la izquierda
                    Log.d("Mensaje","izquierda");
                }
            }
        } else {
            if (Math.abs(diffY) > 100 && Math.abs(velocityY) > 100) {
                if (diffY > 0) {
                    // Deslizamiento hacia abajo
                    Log.d("Mensaje","abajo");
                } else {
                    // Deslizamiento hacia arriba
                    Log.d("Mensaje","arriba");
                }
            }
        }

        return true;
    }
}