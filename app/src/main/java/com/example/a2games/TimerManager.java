package com.example.a2games;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

public class TimerManager {
    private TextView timerTextView;
    private long countdownTime;
    private boolean isCountingDown;
    private Thread countdownThread;

    private TimerListener timerListener;

    public TimerManager(TextView textView) {
        this.timerTextView = textView;
    }

    public void startCountDown(int minutes) {
        long milliseconds = (long) minutes * 60 * 1000; // Convertir minutos a milisegundos
        startCountDown(milliseconds);
    }

    public void startCountDown(long milliseconds) {
        if (!isCountingDown) {
            countdownTime = milliseconds;
            isCountingDown = true;
            countdownThread = new Thread(new CountdownRunnable());
            countdownThread.start();
        }
    }

    public void stopCountDown() {
        isCountingDown = false;
        if (countdownThread != null) {
            countdownThread.interrupt();
        }
    }

    private class CountdownRunnable implements Runnable {
        @Override
        public void run() {
            while (isCountingDown && countdownTime > 0) {
                countdownTime -= 1000; // Resta 1 segundo
                updateTimerText(countdownTime);
                try {
                    Thread.sleep(1000); // Espera 1 segundo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Cuando el tiempo llega a cero, detenemos la cuenta regresiva
            isCountingDown = false;

            // Realiza cualquier acción que desees cuando la cuenta regresiva termina
            timerTextView.post(() -> {
                timerListener.onTimeUp();;
            });
        }
    }

    private void updateTimerText(final long millisecondsUntilFinished) {
        final long secondsUntilFinished = millisecondsUntilFinished / 1000;
        final long minutes = secondsUntilFinished / 60;
        final long seconds = secondsUntilFinished % 60;

        timerTextView.post(() -> {
            String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
            timerTextView.setText("Tiempo restante " + timeLeftFormatted);
        });
    }

    public void setTimerListener(TimerListener listener) {
        this.timerListener = listener;
    }
}
