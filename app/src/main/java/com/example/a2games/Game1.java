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

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 4;
    private Button[][] buttonsGrid = new Button[NUM_ROWS][NUM_COLS];

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

        int buttonCounter = 0;
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                String buttonId = "button" + (++buttonCounter);
                int buttonResId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttonsGrid[row][col] = findViewById(buttonResId);
            }
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttonsGrid[row][col];
                String buttonText = button.getText().toString(); // Obtener texto del botón si tiene alguno
                Log.d("ArrayContent", "Button at row " + row + ", col " + col + ": " + buttonText);
            }
        }

        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
        });
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
                boolean moved = false;
                int[] row = new int[2];
                int[] col = new int[2];

                int visibleCount = 0;
                outerloop:
                for (int r = 0; r < NUM_ROWS; r++) {
                    for (int c = 0; c < NUM_COLS; c++) {
                        if (buttonsGrid[r][c].getVisibility() == View.VISIBLE) {
                            row[visibleCount] = r;
                            col[visibleCount] = c;
                            visibleCount++;
                            if (visibleCount == 2) {
                                break outerloop;
                            }
                        }
                    }
                }

                for (int i = 0; i < 2; i++) {
                    while (col[i] < NUM_COLS - 1) {
                        buttonsGrid[row[i]][col[i]].setVisibility(View.INVISIBLE);
                        col[i]++;
                        buttonsGrid[row[i]][col[i]].setVisibility(View.VISIBLE);
                        moved = true;
                    }
                }



            } else {
                // Fling hacia la izquierda
                Log.d("Gesto", "Fling hacia la izquierda");
                boolean moved = false;

                do {
                    moved = false;

                    // Encuentra la posición actual de los botones visibles en la cuadrícula
                    int[] row = new int[2];
                    int[] col = new int[2];

                    int visibleCount = 0;
                    outerloop:
                    for (int r = 0; r < NUM_ROWS; r++) {
                        for (int c = 0; c < NUM_COLS; c++) {
                            if (buttonsGrid[r][c].getVisibility() == View.VISIBLE) {
                                row[visibleCount] = r;
                                col[visibleCount] = c;
                                visibleCount++;
                                if (visibleCount == 2) {
                                    break outerloop;
                                }
                            }
                        }
                    }

                    // Verifica si los botones pueden moverse hacia la izquierda
                    if (col[0] > 0 && col[1] > 0) {
                        // Oculta los botones en sus posiciones actuales
                        for (int i = 0; i < 2; i++) {
                            buttonsGrid[row[i]][col[i]].setVisibility(View.INVISIBLE);
                        }

                        // Actualiza la posición de los botones hacia la izquierda
                        for (int i = 0; i < 2; i++) {
                            col[i]--;
                            buttonsGrid[row[i]][col[i]].setVisibility(View.VISIBLE);
                            moved = true; // Indica que al menos uno de los botones se ha movido
                        }
                    }
                } while (moved); // Continúa moviendo mientras al menos uno de los botones se mueva
            }
        } else {
            // Fling vertical
            if (velocityY > 0) {
                // Fling hacia abajo
                Log.d("Gesto", "Fling hacia abajo");
                boolean moved = false;

                do {
                    moved = false;

                    int[] row = new int[2];
                    int[] col = new int[2];

                    int visibleCount = 0;
                    outerloop:
                    for (int r = 0; r < NUM_ROWS; r++) {
                        for (int c = 0; c < NUM_COLS; c++) {
                            if (buttonsGrid[r][c].getVisibility() == View.VISIBLE) {
                                row[visibleCount] = r;
                                col[visibleCount] = c;
                                visibleCount++;
                                if (visibleCount == 2) {
                                    break outerloop;
                                }
                            }
                        }
                    }

                    if (row[0] < NUM_ROWS - 1 && row[1] < NUM_ROWS - 1) {
                        for (int i = 0; i < 2; i++) {
                            buttonsGrid[row[i]][col[i]].setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < 2; i++) {
                            row[i]++;
                            buttonsGrid[row[i]][col[i]].setVisibility(View.VISIBLE);
                            moved = true;
                        }
                    }
                } while (moved);

            } else {
                // Fling hacia arriba
                Log.d("Gesto", "Fling hacia arriba");
                boolean moved = false;

                do {
                    moved = false;

                    int[] row = new int[2];
                    int[] col = new int[2];

                    int visibleCount = 0;
                    outerloop:
                    for (int r = 0; r < NUM_ROWS; r++) {
                        for (int c = 0; c < NUM_COLS; c++) {
                            if (buttonsGrid[r][c].getVisibility() == View.VISIBLE) {
                                row[visibleCount] = r;
                                col[visibleCount] = c;
                                visibleCount++;
                                if (visibleCount == 2) {
                                    break outerloop;
                                }
                            }
                        }
                    }

                    if (row[0] > 0 && row[1] > 0) {
                        for (int i = 0; i < 2; i++) {
                            buttonsGrid[row[i]][col[i]].setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < 2; i++) {
                            row[i]--;
                            buttonsGrid[row[i]][col[i]].setVisibility(View.VISIBLE);
                            moved = true;
                        }
                    }
                } while (moved);
            }
        }

        return true;
    }

    public void goBack(View view){
        Intent IntentMain = new Intent(this,MainActivity.class);
        startActivity(IntentMain);
    }

}
