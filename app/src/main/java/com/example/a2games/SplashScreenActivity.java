package com.example.a2games;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2games.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Aplica la animación de fade in al layout de la actividad
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        findViewById(android.R.id.content).startAnimation(fadeInAnimation);

        // Obtiene la referencia a la imagen
        ImageView imageLogo = findViewById(R.id.imageLogo);

        // Define la animación de desplazamiento desde la derecha
        Animation slideFromRightAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_from_right);

        // Aplica la animación a la imagen
        imageLogo.startAnimation(slideFromRightAnimation);

        // Configura un temporizador para cerrar esta actividad después del tiempo especificado
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Este método se ejecutará después del tiempo transcurrido
                // Inicia la actividad principal de la aplicación
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);

                // Cierra esta actividad
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
