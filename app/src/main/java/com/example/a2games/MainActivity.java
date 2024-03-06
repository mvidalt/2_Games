package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<String> gamesList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Obtener el nombre de usuario de SharedPreferences
        String username = sharedPreferences.getString("username", "");

        // Configurar el texto de bienvenida con el nombre de usuario
        TextView textViewProfile = findViewById(R.id.textViewProfile);
        textViewProfile.setText("Bienvenido, " + username);

        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gamesList = new ArrayList<>();
        gamesList.add("2048");
        gamesList.add("Senku");

        gameAdapter = new GameAdapter(gamesList, this);
        recyclerView.setAdapter(gameAdapter);

        // Agrega funcionalidad de arrastre y soltar
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(gamesList, fromPosition, toPosition);
            gameAdapter.notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            // No necesitamos implementar esto para el arrastre y soltar
        }
    };

    public void openProfile(View view) {
        Intent intentProfile = new Intent(this, UserProfile.class);
        startActivity(intentProfile);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
