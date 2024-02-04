package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class Senku extends AppCompatActivity {

    public GridLayout gridLayout;
    private final ImageButton[][] ArrayImageButtons = new ImageButton[7][7];

    public TextView movimientos;

    public int movimientosNum = 0;

    public TextView timer;

    private enum ButtonState {
        ON,
        OFF,
        SELECTED
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.buttonsContainer);
        movimientos = findViewById(R.id.varMoves);
        timer = findViewById(R.id.txtTime);

        createGameButtons();
        startClock();
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
                imageButton.setOnClickListener(v -> {
                    handleImageButtonClick(imageButton, row, col);
                });
            }
        }

        Button buttonNewGame = findViewById(R.id.btnReset);
        buttonNewGame.setOnClickListener(v -> {
            resetGame();
        });
    }

    public void handleImageButtonClick(ImageButton clickedImageButton, int row, int col) {
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
                        // Switch the OFF button to ON and the SELECTED button to OFF
                        clickedImageButton.setImageResource(R.drawable.radio_button_on);
                        clickedImageButton.setTag(ButtonState.ON);

                        ArrayImageButtons[i][j].setImageResource(R.drawable.radio_button_off);
                        ArrayImageButtons[i][j].setTag(ButtonState.OFF);

                        // Switch the ball between ON and SELECTED to OFF
                        switchBallsOff(i, j, row, col);
                        updateMovements();

                        if (isGameOver()) {
                            handleGameOver(); // Método para manejar el juego terminado
                        }
                        if (isGameWinned()) {
                            handleGameWinned();
                        }
                    }
                }
            }
        } else if (ButtonState.SELECTED.equals(clickedImageButton.getTag())) {
            clickedImageButton.setImageResource(R.drawable.radio_button_on);
            clickedImageButton.setTag(ButtonState.ON);
        }
    }

    private void handleGameWinned() {
        timer.setText("¡Has Ganado!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Senku.this);
        builder.setTitle("¡Felicidades!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    recreate();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }


    private void switchBallsOff(int selectedRow, int selectedCol, int offRow, int offCol) {
        int middleRow = (selectedRow + offRow) / 2;
        int middleCol = (selectedCol + offCol) / 2;

        if (ButtonState.ON.equals(ArrayImageButtons[middleRow][middleCol].getTag())) {
            ArrayImageButtons[middleRow][middleCol].setImageResource(R.drawable.radio_button_off);
            ArrayImageButtons[middleRow][middleCol].setTag(ButtonState.OFF);
        }
    }


    private boolean isDistanceTwo(int selectedRow, int selectedCol, int offRow, int offCol) {
        int rowDistance = Math.abs(selectedRow - offRow);
        int colDistance = Math.abs(selectedCol - offCol);
        return (rowDistance == 2 && colDistance == 0) || (rowDistance == 0 && colDistance == 2);
    }

    private boolean isGameOver() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (ButtonState.ON.equals(ArrayImageButtons[i][j].getTag())) {
                    // Verifica hacia arriba
                    if (i > 0 && ButtonState.ON.equals(ArrayImageButtons[i - 1][j].getTag())) {
                        return false; // Hay un botón ON arriba
                    }
                    // Verifica hacia abajo
                    if (i < 6 && ButtonState.ON.equals(ArrayImageButtons[i + 1][j].getTag())) {
                        return false; // Hay un botón ON abajo
                    }
                    // Verifica hacia la izquierda
                    if (j > 0 && ButtonState.ON.equals(ArrayImageButtons[i][j - 1].getTag())) {
                        return false; // Hay un botón ON a la izquierda
                    }
                    // Verifica hacia la derecha
                    if (j < 6 && ButtonState.ON.equals(ArrayImageButtons[i][j + 1].getTag())) {
                        return false; // Hay un botón ON a la derecha
                    }
                }
            }
        }
        return true; // No hay botones ON adyacentes, el juego ha terminado
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
        gridLayout.removeAllViews();
        createGameButtons();
    }

    private void updateMovements() {
        movimientosNum += 1;
        movimientos.setText(String.valueOf(movimientosNum));
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

    private void updateTimerText(long secondsUntilFinished) {
        long minutes = secondsUntilFinished / 60;
        long seconds = secondsUntilFinished % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timer.setText("Tiempo restante: " + timeLeftFormatted);
    }

    @SuppressLint("SetTextI18n")
    private void handleGameOver() {
        timer.setText("¡Game Over!");
        AlertDialog.Builder builder = new AlertDialog.Builder(Senku.this);
        builder.setTitle("¡Has perdido!")
                .setMessage("¿Quieres volver a jugar?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    recreate();
                })
                .setNegativeButton("No", (dialog, which) -> {
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
                    recreate();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(false);

        AlertDialog gameOverDialog = builder.create();
        gameOverDialog.show();
    }


    public void goBack(View view) {
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
    }
}
