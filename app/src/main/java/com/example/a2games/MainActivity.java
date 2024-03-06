package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.a2games.GameAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {


    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista de nombres de juegos
        List<String> gamesList = new ArrayList<>();
        gamesList.add("2048");
        gamesList.add("Senku");

        // Configurar adaptador y proporcionar datos
        gameAdapter = new GameAdapter(gamesList, this); // Pasa "this" como el listener de clics
        recyclerView.setAdapter(gameAdapter);
    }


    @Override
    public void onGameClick(String gameName) {
        if ("2048".equals(gameName)) {
            Intent intent = new Intent(this, Game1.class);
            startActivity(intent);
        } else if ("Senku".equals(gameName)) {
            Intent intent = new Intent(this, Senku.class);
            startActivity(intent);
        }
        // Agrega más condicionales para otros juegos si es necesario
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
