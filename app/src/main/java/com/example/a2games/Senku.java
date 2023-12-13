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
                    imageButton.setTag(R.drawable.radio_button_custom);
                } else {
                    imageButton.setImageResource(R.drawable.radio_button_off);
                    imageButton.setTag(R.drawable.radio_button_off);
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

                        int currentImageResource = (int) imageButton.getTag();
                        boolean hasSelectedButton = false;
                        int selectedButtonIndex = -1;

                        for (int k = 0; k < 33; k++) {
                            if ((int) imageButtons[k].getTag() == R.drawable.radio_button_custom_selected) {
                                hasSelectedButton = true;
                                selectedButtonIndex = k;
                                break;
                            }
                        }

                        if (currentImageResource == R.drawable.radio_button_custom) {
                            // Cambiar el botón 'custom' a 'selected'
                            if (!hasSelectedButton) {
                                for (int k = 0; k < 33; k++) {
                                    if ((int) imageButtons[k].getTag() == R.drawable.radio_button_custom_selected) {
                                        imageButtons[k].setImageResource(R.drawable.radio_button_custom);
                                        imageButtons[k].setTag(R.drawable.radio_button_custom);
                                        break; // Detener el bucle después de cambiar un botón 'selected' a 'custom'
                                    }
                                }
                                imageButton.setImageResource(R.drawable.radio_button_custom_selected);
                                imageButton.setTag(R.drawable.radio_button_custom_selected);


                            }
                        } else if (currentImageResource == R.drawable.radio_button_off && hasSelectedButton) {
                            // Cambiar el botón 'off' a 'custom' solo si hay un botón 'selected'
                            imageButton.setImageResource(R.drawable.radio_button_custom);
                            imageButton.setTag(R.drawable.radio_button_custom);

                            // Restablecer el botón 'selected' a 'custom'
                            imageButtons[selectedButtonIndex].setImageResource(R.drawable.radio_button_off);
                            imageButtons[selectedButtonIndex].setTag(R.drawable.radio_button_off);

                            //buttonIndex = 17
                            //selectedButtonIndex = 19 ( derecha )
                            //selectedButtonIndex = 29 ( abajo )
                            Log.d("Boton", "El boton que aparece: " + (buttonIndex + 1));
                            Log.d("Boton", "El boton que pulsamos: " + (selectedButtonIndex + 1));

                            if (selectedButtonIndex != buttonIndex + 12) {
                                if (selectedButtonIndex > buttonIndex + 1) {
                                    imageButtons[selectedButtonIndex - 1].setImageResource(R.drawable.radio_button_off);
                                    imageButtons[selectedButtonIndex - 1].setTag(R.drawable.radio_button_off);
                                } else if (buttonIndex + 1 > selectedButtonIndex) {
                                    imageButtons[selectedButtonIndex + 1].setImageResource(R.drawable.radio_button_off);
                                    imageButtons[selectedButtonIndex + 1].setTag(R.drawable.radio_button_off);
                                }
                            } if(selectedButtonIndex == buttonIndex +12){
                                imageButtons[selectedButtonIndex -5].setImageResource(R.drawable.radio_button_off);
                                imageButtons[selectedButtonIndex -5].setTag(R.drawable.radio_button_off);
                            }if(selectedButtonIndex == buttonIndex -12) {
                                imageButtons[selectedButtonIndex + 5].setImageResource(R.drawable.radio_button_off);
                                imageButtons[selectedButtonIndex + 5].setTag(R.drawable.radio_button_off);
                            }

                        } else if (currentImageResource == R.drawable.radio_button_custom_selected) {
                            // Cambiar el botón 'selected' a 'custom'
                            imageButton.setImageResource(R.drawable.radio_button_custom);
                            imageButton.setTag(R.drawable.radio_button_custom);


                        }
                    }
                });
            }
        }
    }

    private void handleButtonClick(int buttonIndex) {
        int buttonId = buttonIndex + 1;
    }

    public void goBack(View view) {
        Intent IntentMain = new Intent(this, MainActivity.class);
        startActivity(IntentMain);
    }

}
