package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Senku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);

        final ImageButton[] imageButtons = new ImageButton[33];

        for (int i = 1; i <= 33; i++) {
            int resId = getResources().getIdentifier("rb" + i, "id", getPackageName());
            final ImageButton imageButton = findViewById(resId);
            if (imageButton != null) {
                if (i != 17) {
                    imageButton.setImageResource(R.drawable.radio_button_custom);
                } else {
                    imageButton.setImageResource(R.drawable.radio_button_off);
                }
                imageButtons[i - 1] = imageButton;

                imageButton.setOnClickListener(v -> {
                    int buttonIndex = -1;
                    for (int j = 0; j < 33; j++) {
                        if (imageButtons[j] == imageButton) {
                            buttonIndex = j;
                            break;
                        }
                    }
                    if (buttonIndex != -1) {
                        handleButtonClick(buttonIndex);
                        imageButton.setImageResource(R.drawable.radio_button_off);
                    }
                });
            }
        }
    }

    private void handleButtonClick(int buttonIndex) {
        int buttonId = buttonIndex + 1;
        Log.d("BotonPresionado", "Me han pulsado. ID: " + buttonId);
    }

    public void goBack(View view){
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
    }

}
