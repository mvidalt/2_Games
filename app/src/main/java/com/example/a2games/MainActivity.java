package com.example.a2games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.OnGameClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    private List<String> gamesList;
    private SharedPreferences sharedPreferences;

    private ImageView usuarioImg;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        String username = sharedPreferences.getString("username", "");

        TextView textViewProfile = findViewById(R.id.textViewProfile);
        textViewProfile.setText("Welcome \n"
                + username);

        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gamesList = new ArrayList<>();
        gamesList.add("2048");
        gamesList.add("Senku");

        gameAdapter = new GameAdapter(gamesList, this);
        recyclerView.setAdapter(gameAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        usuarioImg = findViewById(R.id.usuarioImg);

        imagePath = loadImagePathFromInternalStorage();
        if (imagePath != null && !imagePath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                usuarioImg.setImageBitmap(bitmap);
            }
        }
    }

    private String loadImagePathFromInternalStorage() {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getDir("profile_images", Context.MODE_PRIVATE);
        File imagePath = new File(directory, "profile.jpg");
        if (imagePath.exists()) {
            return imagePath.getAbsolutePath();
        }
        return null;
    }

    @Override
    public void onGameClick(String gameName) {
        if ("2048".equals(gameName)) {
            Intent intent = new Intent(this, Game2048.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else if ("Senku".equals(gameName)) {
            Intent intent = new Intent(this, Senku.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
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
        }
    };

    public void openProfile(View view) {
        Intent intentProfile = new Intent(this, UserProfile.class);
        startActivity(intentProfile);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void logOut(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
