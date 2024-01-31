package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class Senku extends AppCompatActivity {

    public GridLayout gridLayout;
    private final ImageButton[][] ArrayImageButtons = new ImageButton[7][7];

    private enum ButtonState {
        ON,
        OFF
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.buttonsContainer);
        createGameButtons();
    }

    private void createGameButtons() {
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
                    handleImageButtonClick(imageButton);
                });
            }
        }
    }

    public void handleImageButtonClick(ImageButton clickedImageButton) {
        if (ButtonState.ON.equals(clickedImageButton.getTag())) {
            clickedImageButton.setImageResource(R.drawable.radio_button_custom);
        }
    }

    public void goBack(View view) {
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
    }
}
