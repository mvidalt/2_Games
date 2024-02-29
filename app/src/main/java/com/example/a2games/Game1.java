package com.example.a2games;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;

public class Game1 extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private GridLayout gridLayout;

    private Button[][] Arraybuttons;

    private GestureDetector gestureDetector;

    final int rowCount = 4;
    final int columnCount = 4;

    TextView scoreText;

    TextView bestScoreText;

    private int score = 0;

    private HashMap<String, Integer> colorMap = new HashMap<>();


    private TextView timer;


    private SharedPreferences  sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game1_layout);

        gestureDetector = new GestureDetector(this, this);
        scoreText = findViewById(R.id.score);
        gridLayout = findViewById(R.id.gridLayout);
        timer = findViewById(R.id.timer);
        bestScoreText = findViewById(R.id.scoretotal);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int savedBestScore = sharedPreferences.getInt("score2048", 0);
        bestScoreText.setText(String.valueOf(savedBestScore));

        createGameButtons();


        colorMap = new HashMap<>();
        colorMap.put("0", Color.parseColor("#cdc1b4"));
        colorMap.put("2", Color.parseColor("#eee4da"));
        colorMap.put("4", Color.parseColor("#eee1c9"));
        colorMap.put("8", Color.parseColor("#f3b27a"));
        colorMap.put("16", Color.parseColor("#f69664"));
        colorMap.put("32", Color.parseColor("#f77c5f"));
        colorMap.put("64", Color.parseColor("#f75f3b"));
        colorMap.put("128", Color.parseColor("#edcf72"));
        colorMap.put("256", Color.parseColor("#edcc61"));
        colorMap.put("512", Color.parseColor("#edc851"));
        colorMap.put("1024", Color.parseColor("#edc53f"));
        colorMap.put("2048", Color.parseColor("#edc22e"));

        startClock();

        Button buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonNewGame.setOnClickListener(v -> {
            restartGame();
        });
    }

    private void createGameButtons() {
        final int margin = 16;

        Arraybuttons = new Button[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Button button = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));


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
                button.setClickable(false);
                Arraybuttons[i][j] = button;
                gridLayout.addView(button);
            }
        }

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
                        updateButtonTextVisibility();
                        updateScore(newVal);
                        moved = true;
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
            if (isGameLost()) {
                showGameOverDialog();
                saveBestScore();
            }
            if (isGameWinned()) {
                showGameWinned();
                saveBestScore();
            }
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
                        updateButtonTextVisibility();
                        updateScore(newVal);
                        updateScore(newVal);
                        moved = true;
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
            if (isGameLost()) {
                showGameOverDialog();
                saveBestScore();
            }
            if (isGameWinned()) {
                showGameWinned();
                saveBestScore();
            }

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
                        updateButtonTextVisibility();
                        updateScore(newVal);
                        moved = true;
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
            if (isGameLost()) {
                showGameOverDialog();
                saveBestScore();
            }
            if (isGameWinned()) {
                showGameWinned();
                saveBestScore();
            }
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
                        updateButtonTextVisibility();
                        updateScore(newVal);
                        moved = true;
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
            if (isGameLost()) {
                showGameOverDialog();
                saveBestScore();
            }
            if (isGameWinned()) {
                showGameWinned();
                saveBestScore();
            }

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

        int newValue = random.nextFloat() < 0.90 ? 2 : 4;

        Arraybuttons[randomRow][randomCol].setText(String.valueOf(newValue));

        updateButtonTextVisibility();
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

    private void updateScore(int addedValue) {
        score += addedValue;
        scoreText.setText(String.valueOf(score));
    }

    private void saveBestScore() {
        int memoryScore = sharedPreferences.getInt("score", 0);
        if (score > memoryScore) {
            memoryScore = score;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("score2048", memoryScore);
            editor.apply();
            bestScoreText.setText(String.valueOf(memoryScore));
        }
    }


    private boolean isGameLost() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String currentText = Arraybuttons[i][j].getText().toString();


                if (j < columnCount - 1 && (Arraybuttons[i][j + 1].getText().toString().equals("0") || Arraybuttons[i][j + 1].getText().toString().equals(currentText))) {
                    return false;
                }


                if (i < rowCount - 1 && (Arraybuttons[i + 1][j].getText().toString().equals("0") || Arraybuttons[i + 1][j].getText().toString().equals(currentText))) {
                    return false;
                }


                if (i > 0 && (Arraybuttons[i - 1][j].getText().toString().equals("0") || Arraybuttons[i - 1][j].getText().toString().equals(currentText))) {
                    return false;
                }

                if (j > 0 && (Arraybuttons[i][j - 1].getText().toString().equals("0") || Arraybuttons[i][j - 1].getText().toString().equals(currentText))) {
                    return false;
                }

            }
        }

        return true;
    }


    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Juego Terminado!")
                .setMessage("Has perdido. ¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    restartGame();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }


    private boolean isGameWinned() {

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String currentText = Arraybuttons[i][j].getText().toString();
                if (currentText.equals("2048")) {
                    return true;
                }
            }
        }

        return false;
    }

    private void showGameWinned() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Has ganado, felicidades!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    restartGame();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }

    private void startClock() {
        new Thread(() -> {
            long totalTimeSeconds = 600;
            long intervalSeconds = 1;

            while (totalTimeSeconds > 0) {
                try {
                    Thread.sleep(intervalSeconds * 1000);
                    totalTimeSeconds -= intervalSeconds;

                    long finalTotalTimeSeconds = totalTimeSeconds;
                    runOnUiThread(() -> updateTimerText(finalTotalTimeSeconds));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            runOnUiThread(this::handleTimeUp);
        }).start();
    }

    @SuppressLint("SetTextI18n")
    private void updateTimerText(long secondsUntilFinished) {
        long minutes = secondsUntilFinished / 60;
        long seconds = secondsUntilFinished % 60;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText("Tiempo restante: " + timeLeftFormatted);
    }



    @SuppressLint("SetTextI18n")
    private void handleTimeUp() {
        timer.setText("¡Cuenta regresiva terminada!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Game1.this);
        builder.setTitle("¡Se acabó el tiempo!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    restartGame();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }

    private void restartGame() {
        // Reiniciar la puntuación
        score = 0;
        scoreText.setText("0");

        // Reiniciar el temporizador
        startClock();

        // Limpiar los botones del juego
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                Arraybuttons[i][j].setText("0");
            }
        }

        // Generar dos nuevos números
        generateNewNumber();
        generateNewNumber();
    }
}