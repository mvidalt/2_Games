package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class Game1 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static final int NUM_BUTTONS = 16;
    private GestureDetector gestureDetector;

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
        gestureDetector = new GestureDetector(this, this);

        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d("log","me han pulsado");
        }
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // No hacemos nada aquí
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // No hacemos nada aquí
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // No hacemos nada aquí
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("log","me han pulsado mucho");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Se llama cuando se detecta un gesto de fling
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            // Fling horizontal
            if (velocityX > 0) {
                // Fling hacia la derecha
                Log.d("Gesto", "Fling hacia la derecha");
            } else {
                // Fling hacia la izquierda
                Log.d("Gesto", "Fling hacia la izquierda");
            }
        } else {
            // Fling vertical
            if (velocityY > 0) {
                // Fling hacia abajo
                Log.d("Gesto", "Fling hacia abajo");
            } else {
                // Fling hacia arriba
                Log.d("Gesto", "Fling hacia arriba");
            }
        }
        return true;
    }
}