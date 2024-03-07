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
        long milliseconds = (long) minutes * 60 * 1000;
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
                countdownTime -= 1000;
                updateTimerText(countdownTime);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            isCountingDown = false;

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
