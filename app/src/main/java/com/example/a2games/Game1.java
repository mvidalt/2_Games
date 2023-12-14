package com.example.a2games;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.Random;

public class Game1 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GridLayout gridLayout;
    private Button[][] buttonsArray;

    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_layout);

        gestureDetector = new GestureDetector(this, this);

        gridLayout = findViewById(R.id.gridLayout);
        createGameButtons();
        // Inicializar el array de botones
        buttonsArray = new Button[4][4];

        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
        });
    }

    private void createGameButtons() {
        // Crear botones y agregarlos a la cuadrícula
        final int rowCount = 4;
        final int columnCount = 4;
        final int margin = 16; // Define el margen vertical entre los botones

        // Crear un arreglo bidimensional de botones para almacenar referencias
        Button[][] buttons = new Button[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Button button = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(i), GridLayout.spec(j));

                // Ajustar el margen vertical si es la primera fila
                if (i == 0) {
                    params.setMargins(0, margin, 0, 0);
                } else {
                    params.setMargins(0, 0, 0, 0);
                }

                button.setLayoutParams(params);

                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#eee4da"));
                button.setText("0");
                button.setBackgroundTintList(colorStateList);
                button.setTextColor(Color.parseColor("#776e65"));
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                button.setAllCaps(false);

                gridLayout.addView(button);

                // Almacenar referencias a los botones en la matriz
                buttonsArray[i][j] = button;
            }
        }


    }

    public void goBack(View view) {
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("log", "me han pulsado");
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
        Log.d("log", "me han pulsado mucho");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Se llama cuando se detecta un gesto de fling
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            // Fling horizontal
            if (velocityX > 0) {
                // Fling hacia la derecha
                Log.d("Gesto", "Fling hacia la derecha");
                moveRight();
                printButtonsArray();
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

    private void moveRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j >= 0; j--) {
                if (buttonsArray[i][j] != null && !buttonsArray[i][j].getText().equals("0")) {
                    int column = j;
                    while (column < 3) {
                        int nextColumn = column + 1;
                        if (buttonsArray[i][nextColumn].getText().equals("0")) {
                            buttonsArray[i][nextColumn].setText(buttonsArray[i][column].getText());
                            buttonsArray[i][column].setText("0");
                            column = nextColumn;
                        } else if (buttonsArray[i][nextColumn].getText().equals(buttonsArray[i][column].getText())) {
                            int value = Integer.parseInt(buttonsArray[i][column].getText().toString()) * 2;
                            buttonsArray[i][nextColumn].setText(String.valueOf(value));
                            buttonsArray[i][column].setText("0");
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void printButtonsArray() {
        for (int i = 0; i < buttonsArray.length; i++) {
            for (int j = 0; j < buttonsArray[i].length; j++) {
                if (buttonsArray[i][j] != null) {
                    String buttonText = buttonsArray[i][j].getText().toString();
                    Log.d("Button", "Button at [" + i + "][" + j + "]: " + buttonText);
                } else {
                    Log.d("Button", "Button at [" + i + "][" + j + "]: null");
                }
            }
        }
    }

}