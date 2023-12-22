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

import java.util.HashMap;
import java.util.Random;

public class Game1 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GridLayout gridLayout;

    private Button[][] Arraybuttons;

    private GestureDetector gestureDetector;

    final int rowCount = 4;
    final int columnCount = 4;

    private HashMap<String, Integer> colorMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_layout);

        gestureDetector = new GestureDetector(this, this);

        gridLayout = findViewById(R.id.gridLayout);
        createGameButtons();

        colorMap = new HashMap<>();
        colorMap.put("0", Color.parseColor("#cdc1b4"));
        colorMap.put("2", Color.parseColor("#eee4da"));
        colorMap.put("4", Color.parseColor("#eee1c9"));
        colorMap.put("8", Color.parseColor("#f3b27a"));
        colorMap.put("16", Color.parseColor("#f69664"));
        colorMap.put("32", Color.parseColor("#f77c5f"));
        colorMap.put("64", Color.parseColor("#f75f3b"));



        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            finish();

            Intent intent = new Intent(Game1.this, Game1.class);
            startActivity(intent);
        });
    }

    private void createGameButtons() {
        // Crear botones y agregarlos a la cuadrícula
        final int margin = 16; // Define el margen vertical entre los botones

        // Crear un arreglo bidimensional de botones para almacenar referencias
        Arraybuttons = new Button[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Button button = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                // Ajustar el margen vertical si es la primera fila
                if (i == 0) {
                    params.setMargins(0, margin, 0, 0);
                } else {
                    params.setMargins(0, 0, 0, margin);
                }

                button.setLayoutParams(params);

                ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#eee4da"));
                button.setText("0");
                button.setBackgroundTintList(colorStateList);
                button.setTextColor(Color.parseColor("#776e65"));
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
                button.setAllCaps(false);

                Arraybuttons[i][j] = button; // Agregar el botón al array
                gridLayout.addView(button);
            }
        }

        // Establecer dos botones aleatorios con el valor "2"
        Random random = new Random();
        int count = 0;

        while (count < 2) {
            int randomRow = random.nextInt(rowCount);
            int randomCol = random.nextInt(columnCount);

            if (Arraybuttons[randomRow][randomCol].getText().equals("0")) {
                Arraybuttons[randomRow][randomCol].setText("2");
                count++;
            }
            updateButtonTextVisibility();
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
                moveNumbersRight();
            } else {
                // Fling hacia la izquierda
                Log.d("Gesto", "Fling hacia la izquierda");
                moveNumbersLeft();
            }
        } else {
            // Fling vertical
            if (velocityY > 0) {
                // Fling hacia abajo
                Log.d("Gesto", "Fling hacia abajo");
                moveNumbersDown();
            } else {
                // Fling hacia arriba
                Log.d("Gesto", "Fling hacia arriba");
                moveNumbersUp();
            }
        }
        return true;
    }

    private void moveNumbersRight() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 4 - 1; j >= 0; j--) {
                String buttonText = Arraybuttons[i][j].getText().toString();
                if (!buttonText.equals("0")) {
                    int k = j + 1;
                    while (k < 4 && Arraybuttons[i][k].getText().equals("0")) {
                        k++;
                    }
                    if (k < 4 && Arraybuttons[i][k].getText().equals(buttonText)) {
                        int newVal = Integer.parseInt(buttonText) * 2;
                        Arraybuttons[i][k].setText(String.valueOf(newVal));
                        Arraybuttons[i][j].setText("0");
                    } else {
                        k = j + 1;
                        while (k < 4 && Arraybuttons[i][k].getText().equals("0")) {
                            k++;
                        }
                        if (k - 1 != j) {
                            Arraybuttons[i][k - 1].setText(buttonText);
                            Arraybuttons[i][j].setText("0");
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            updateButtonTextVisibility();
            generateNewNumber();
        }
    }

    private void moveNumbersLeft() {
        boolean moved = false;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String buttonText = Arraybuttons[i][j].getText().toString();
                if (!buttonText.equals("0")) {
                    int k = j - 1;
                    while (k >= 0 && Arraybuttons[i][k].getText().equals("0")) {
                        k--;
                    }
                    if (k >= 0 && Arraybuttons[i][k].getText().equals(buttonText)) {
                        int newVal = Integer.parseInt(buttonText) * 2;
                        Arraybuttons[i][k].setText(String.valueOf(newVal));
                        Arraybuttons[i][j].setText("0");
                    } else {
                        k = j - 1;
                        while (k >= 0 && Arraybuttons[i][k].getText().equals("0")) {
                            k--;
                        }
                        if (k + 1 != j) {
                            Arraybuttons[i][k + 1].setText(buttonText);
                            Arraybuttons[i][j].setText("0");
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            updateButtonTextVisibility();
            generateNewNumber();
        }
    }

    private void moveNumbersUp() {
        boolean moved = false;
        for (int j = 0; j < columnCount; j++) {
            for (int i = 0; i < rowCount; i++) {
                String buttonText = Arraybuttons[i][j].getText().toString();
                if (!buttonText.equals("0")) {
                    int k = i - 1;
                    while (k >= 0 && Arraybuttons[k][j].getText().equals("0")) {
                        k--;
                    }
                    if (k >= 0 && Arraybuttons[k][j].getText().equals(buttonText)) {
                        int newVal = Integer.parseInt(buttonText) * 2;
                        Arraybuttons[k][j].setText(String.valueOf(newVal));
                        Arraybuttons[i][j].setText("0");
                    } else {
                        k = i - 1;
                        while (k >= 0 && Arraybuttons[k][j].getText().equals("0")) {
                            k--;
                        }
                        if (k + 1 != i) {
                            Arraybuttons[k + 1][j].setText(buttonText);
                            Arraybuttons[i][j].setText("0");
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            updateButtonTextVisibility();
            generateNewNumber();
        }
    }

    private void moveNumbersDown() {
        boolean moved = false;
        for (int j = 0; j < columnCount; j++) {
            for (int i = rowCount - 1; i >= 0; i--) {
                String buttonText = Arraybuttons[i][j].getText().toString();
                if (!buttonText.equals("0")) {
                    int k = i + 1;
                    while (k < rowCount && Arraybuttons[k][j].getText().equals("0")) {
                        k++;
                    }
                    if (k < rowCount && Arraybuttons[k][j].getText().equals(buttonText)) {
                        int newVal = Integer.parseInt(buttonText) * 2;
                        Arraybuttons[k][j].setText(String.valueOf(newVal));
                        Arraybuttons[i][j].setText("0");
                    } else {
                        k = i + 1;
                        while (k < rowCount && Arraybuttons[k][j].getText().equals("0")) {
                            k++;
                        }
                        if (k - 1 != i) {
                            Arraybuttons[k - 1][j].setText(buttonText);
                            Arraybuttons[i][j].setText("0");
                            moved = true;
                        }
                    }
                }
            }
        }
        if (moved) {
            updateButtonTextVisibility();
            generateNewNumber();
        }
    }

    private void generateNewNumber() {

        Random random = new Random();
        int randomRow = random.nextInt(rowCount);
        int randomCol = random.nextInt(columnCount);

        while (!Arraybuttons[randomRow][randomCol].getText().equals("0")) {
            randomRow = random.nextInt(rowCount);
            randomCol = random.nextInt(columnCount);
        }

        // Generar aleatoriamente 2 o 4 con probabilidades del 50% cada uno
        int newValue = random.nextFloat() < 0.5 ? 2 : 4;

        Arraybuttons[randomRow][randomCol].setText(String.valueOf(newValue));

        updateButtonTextVisibility(); // Actualizar la visibilidad del texto
    }

    private void updateButtonTextVisibility() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String value = Arraybuttons[i][j].getText().toString();
                if (value.equals("0")) {
                    Arraybuttons[i][j].setTextColor(Color.TRANSPARENT);
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#cdc1b4"));
                    Arraybuttons[i][j].setBackgroundTintList(colorStateList);
                } else {
                    int color = colorMap.getOrDefault(value, Color.parseColor("#eee4da"));
                    Arraybuttons[i][j].setTextColor(Color.parseColor("#776e65"));
                    Arraybuttons[i][j].setBackgroundTintList(ColorStateList.valueOf(color));
                }
            }
        }
    }

}