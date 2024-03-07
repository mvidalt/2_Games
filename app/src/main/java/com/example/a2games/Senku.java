package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Senku extends AppCompatActivity implements TimerListener {

    public GridLayout gridLayout;
    private final ImageButton[][] ArrayImageButtons = new ImageButton[7][7];

    public TextView scoretxt;

    public int score = 0;

    public TextView timer;

    private enum ButtonState {
        ON,
        OFF,
        SELECTED
    }


    private SharedPreferences sharedPreferences;


    TextView bestScoreText;

    private final ImageButton[][] backupImageButtons = new ImageButton[7][7];

    private ImageView imageBack;

    private TimerManager timerManager;

    private LinearLayout layoutPaso_Atras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.buttonsContainer);
        scoretxt = findViewById(R.id.puntuacion);
        scoretxt.setText("0");
        timer = findViewById(R.id.txtTime);
        bestScoreText = findViewById(R.id.BestScore);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int savedBestScore = sharedPreferences.getInt("scoreSenku", 0);
        bestScoreText.setText(String.valueOf(savedBestScore));
        lowerLayerButtons();
        createGameButtons();
        layoutPaso_Atras = findViewById(R.id.layoutPaso_Atras);
        imageBack = findViewById(R.id.boton_paso_atras);
        layoutPaso_Atras.setVisibility(View.INVISIBLE);
        imageBack.setOnClickListener(v -> {
            layoutPaso_Atras.setVisibility(View.INVISIBLE);
            imageBack.setClickable(false);
            restoreArrayButtonsFromBackup();
            updateUI();
            updateScore(-1);
        });
        timerManager = new TimerManager(timer);
        timerManager.setTimerListener(this);
        timerManager.startCountDown(1);
    }

    private void createGameButtons() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                final int row = i;
                final int col = j;
                ImageButton imageButton = new ImageButton(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                int widthInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 49, getResources().getDisplayMetrics());
                int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, getResources().getDisplayMetrics());
                params.width = widthInDp;
                params.height = heightInDp;

                imageButton.setLayoutParams(params);
                imageButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));

                if ((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 5 || j == 6)) {
                    imageButton.setImageDrawable(null);
                } else {
                    if (i == 3 && j == 3) {
                        imageButton.setImageResource(R.drawable.radio_button_off);
                        imageButton.setTag(ButtonState.OFF);
                    } else {
                        imageButton.setImageResource(R.drawable.radio_button_on);
                        imageButton.setTag(ButtonState.ON);
                    }
                }

                ArrayImageButtons[i][j] = imageButton;

                gridLayout.addView(imageButton);
                imageButton.setOnClickListener(v -> handleImageButtonClick(imageButton, row, col));
            }
        }

        Button buttonNewGame = findViewById(R.id.btnReset);
        buttonNewGame.setOnClickListener(v -> resetGame());

    }

    private void lowerLayerButtons() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ImageButton imageButton = new ImageButton(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                int widthInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 49, getResources().getDisplayMetrics());
                int heightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 47, getResources().getDisplayMetrics());
                params.width = widthInDp;
                params.height = heightInDp;

                imageButton.setLayoutParams(params);
                imageButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FFFFFF")));

                if ((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 5 || j == 6)) {
                    imageButton.setImageDrawable(null);
                } else {
                        imageButton.setImageResource(R.drawable.radio_button_off);
                        imageButton.setTag(ButtonState.OFF);

                }

                gridLayout.addView(imageButton);
            }
        }
    }


    public void handleImageButtonClick(ImageButton clickedImageButton, int row, int col) {
        backupArrayButtons();
        if (!isAnyButtonSelected()) {
            if (ButtonState.ON.equals(clickedImageButton.getTag())) {
                clickedImageButton.setImageResource(R.drawable.radio_button_custom);
                clickedImageButton.setTag(ButtonState.SELECTED);
            }
        } else if (ButtonState.OFF.equals(clickedImageButton.getTag())) {
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (ButtonState.SELECTED.equals(ArrayImageButtons[i][j].getTag()) &&
                            isDistanceTwo(i, j, row, col)) {


                        ArrayImageButtons[i][j].setImageResource(R.drawable.radio_button_off);
                        ArrayImageButtons[i][j].setTag(ButtonState.OFF);

                        switchBallsOff(clickedImageButton,i, j, row, col);
                        updateScore(+1);


                    }
                }
            }
        } else if (ButtonState.SELECTED.equals(clickedImageButton.getTag())) {
            clickedImageButton.setImageResource(R.drawable.radio_button_on);
            clickedImageButton.setTag(ButtonState.ON);
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleGameWinned() {
        timer.setText("¡Has Ganado!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Senku.this);
        builder.setTitle("¡Felicidades! Has Ganado")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> resetGame())
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }


    private void switchBallsOff(ImageButton clickedImageButton, int selectedRow, int selectedCol, int offRow, int offCol) {
        int middleRow = (selectedRow + offRow) / 2;
        int middleCol = (selectedCol + offCol) / 2;

        if (ButtonState.ON.equals(ArrayImageButtons[middleRow][middleCol].getTag())) {
            int startX = ArrayImageButtons[selectedRow][selectedCol].getLeft() - ArrayImageButtons[middleRow][middleCol].getLeft();
            int startY = ArrayImageButtons[selectedRow][selectedCol].getTop() - ArrayImageButtons[middleRow][middleCol].getTop();
            int endX = ArrayImageButtons[offRow][offCol].getLeft() - ArrayImageButtons[middleRow][middleCol].getLeft();
            int endY = ArrayImageButtons[offRow][offCol].getTop() - ArrayImageButtons[middleRow][middleCol].getTop();

            TranslateAnimation translateAnimation = new TranslateAnimation(startX, endX, startY, endY);
            translateAnimation.setDuration(500);

            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ArrayImageButtons[middleRow][middleCol].setImageResource(R.drawable.radio_button_off);
                    ArrayImageButtons[middleRow][middleCol].setTag(ButtonState.OFF);

                    clickedImageButton.setImageResource(R.drawable.radio_button_on);
                    clickedImageButton.setTag(ButtonState.ON);

                    if (isGameWinned()) {
                        handleGameWinned();
                        saveBestScore();
                    } else if (isGameOver()) {
                        handleGameOver();
                        saveBestScore();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            ArrayImageButtons[middleRow][middleCol].startAnimation(translateAnimation);
            layoutPaso_Atras.setVisibility(View.VISIBLE);
            imageBack.setClickable(true);
        }
    }





    private boolean isDistanceTwo(int selectedRow, int selectedCol, int offRow, int offCol) {
        int middleRow = (selectedRow + offRow) / 2;
        int middleCol = (selectedCol + offCol) / 2;

        if (ButtonState.ON.equals(ArrayImageButtons[middleRow][middleCol].getTag())) {
            int rowDistance = Math.abs(selectedRow - offRow);
            int colDistance = Math.abs(selectedCol - offCol);
            return (rowDistance == 2 && colDistance == 0) || (rowDistance == 0 && colDistance == 2);
        }

        return false;
    }

    private boolean isGameOver() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (ButtonState.ON.equals(ArrayImageButtons[i][j].getTag())) {
                    // Verifica hacia arriba
                    if (i > 1 && ButtonState.ON.equals(ArrayImageButtons[i - 1][j].getTag()) && ButtonState.OFF.equals(ArrayImageButtons[i - 2][j].getTag())) {
                        return false;
                    }
                    // Verifica hacia abajo
                    if (i < 5 && ButtonState.ON.equals(ArrayImageButtons[i + 1][j].getTag()) && ButtonState.OFF.equals(ArrayImageButtons[i + 2][j].getTag())) {
                        return false;
                    }
                    // Verifica hacia la izquierda
                    if (j > 1 && ButtonState.ON.equals(ArrayImageButtons[i][j - 1].getTag()) && ButtonState.OFF.equals(ArrayImageButtons[i][j - 2].getTag())) {
                        return false;
                    }
                    // Verifica hacia la derecha
                    if (j < 5 && ButtonState.ON.equals(ArrayImageButtons[i][j + 1].getTag()) && ButtonState.OFF.equals(ArrayImageButtons[i][j + 2].getTag())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }





    private boolean isGameWinned() {
        int count = 0;
        for (ImageButton[] row : ArrayImageButtons) {
            for (ImageButton button : row) {
                if (ButtonState.ON.equals(button.getTag())) {
                    count++;
                }
            }
        }
        return count == 1;
    }




    private boolean isAnyButtonSelected() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (ButtonState.SELECTED.equals(ArrayImageButtons[i][j].getTag())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void resetGame() {
        score = 0;
        scoretxt.setText(String.valueOf(score));
        stopTimer();
        timerManager.startCountDown(5);
        clearButtonStates();
        layoutPaso_Atras.setVisibility(View.INVISIBLE);
    }

    private void clearButtonStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ImageButton button = ArrayImageButtons[i][j];
                if ((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 5 || j == 6)) {
                    button.setImageDrawable(null);
                } else {
                    if (i == 3 && j == 3) {
                        button.setImageResource(R.drawable.radio_button_off);
                        button.setTag(ButtonState.OFF);
                    } else {
                        button.setImageResource(R.drawable.radio_button_on);
                        button.setTag(ButtonState.ON);
                    }
                }
            }
            backupArrayButtons();
        }
    }

    private void backupArrayButtons() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                backupImageButtons[i][j] = new ImageButton(this);
                backupImageButtons[i][j].setImageDrawable(ArrayImageButtons[i][j].getDrawable());
                backupImageButtons[i][j].setTag(ArrayImageButtons[i][j].getTag());
                backupImageButtons[i][j].setClickable(ArrayImageButtons[i][j].isClickable());
            }
        }
    }

    private void restoreArrayButtonsFromBackup() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ArrayImageButtons[i][j].setImageDrawable(backupImageButtons[i][j].getDrawable());
                ArrayImageButtons[i][j].setTag(backupImageButtons[i][j].getTag());
                ArrayImageButtons[i][j].setClickable(backupImageButtons[i][j].isClickable());
            }
        }
    }



    private void updateUI() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                ImageButton button = ArrayImageButtons[i][j];
                if ((i == 0 || i == 1 || i == 5 || i == 6) && (j == 0 || j == 1 || j == 5 || j == 6)) {
                    button.setImageDrawable(null);
                } else {
                    if (ButtonState.OFF.equals(button.getTag())) {
                        button.setImageResource(R.drawable.radio_button_off);
                    } else if (ButtonState.ON.equals(button.getTag())) {
                        button.setImageResource(R.drawable.radio_button_on);
                    } else if (ButtonState.SELECTED.equals(button.getTag())) {
                        button.setImageResource(R.drawable.radio_button_custom);
                    }
                }
            }
        }
    }


    private void updateScore(int value) {
        score += value;
        scoretxt.setText(String.valueOf(score));
    }

    private void saveBestScore() {
        int memoryScore = sharedPreferences.getInt("scoreSenku", 0);
        if (score > memoryScore) {
            memoryScore = score;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("scoreSenku", memoryScore);
            editor.apply();
            bestScoreText.setText(String.valueOf(memoryScore));
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer() {
        if (timerManager != null) {
            timerManager.stopCountDown();
        }
    }


    @SuppressLint("SetTextI18n")
    private void updateTimerText(long secondsUntilFinished) {
        long minutes = secondsUntilFinished / 60;
        long seconds = secondsUntilFinished % 60;

        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText("Tiempo restante: " + timeLeftFormatted);
    }

    @SuppressLint("SetTextI18n")
    private void handleGameOver() {
        timer.setText("¡Game Over!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Senku.this);
        builder.setTitle("¡Has perdido!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    resetGame();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void handleTimeUp() {
        timer.setText("¡Cuenta regresiva terminada!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Senku.this);
        builder.setTitle("¡Se acabó el tiempo!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    resetGame();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    goBack(null);
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }




    public void goBack(View view) {
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onTimeUp() {
        handleTimeUp();
    }

}
