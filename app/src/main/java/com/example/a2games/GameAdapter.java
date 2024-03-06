package com.example.a2games;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private static List<String> gamesList;
    private OnGameClickListener onGameClickListener;

    public GameAdapter(List<String> gamesList, OnGameClickListener listener) {
        this.gamesList = gamesList;
        this.onGameClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_card, parent, false);
        return new ViewHolder(view, onGameClickListener); // Pasar el listener al constructor
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String game = gamesList.get(position);
        holder.gameTextView.setText(game);
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameTextView;
        OnGameClickListener onGameClickListener;

        public ViewHolder(@NonNull View itemView, OnGameClickListener listener) {
            super(itemView);
            gameTextView = itemView.findViewById(R.id.gameTextView);
            onGameClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String gameName = gamesList.get(position);
                onGameClickListener.onGameClick(gameName);
            }
        }

    }

    public interface OnGameClickListener {
        void onGameClick(String gameName);
    }

}
