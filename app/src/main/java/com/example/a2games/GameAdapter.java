package com.example.a2games;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
        return new ViewHolder(view, onGameClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String game = gamesList.get(position);
        holder.gameNameTextView.setText(game);

        String gameObjective = getGameObjective(game);
        holder.gameObjectiveTextView.setText(gameObjective);

        if ("2048".equals(game)) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.color2048));
        } else if ("Senku".equals(game)) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorSenku));
        }

        switch (game) {
            case "2048":
                holder.gameIconImageView.setImageResource(R.drawable.icon2048);
                break;
            case "Senku":
                holder.gameIconImageView.setImageResource(R.drawable.senku_icon);
                break;
            default:
                holder.gameIconImageView.setImageResource(R.drawable.ic_launcher_background);
                break;
        }
    }

    private String getGameObjective(String gameName) {
        switch (gameName) {
            case "2048":
                return "Combina números para obtener el 2048.";
            case "Senku":
                return "Elimina las piezas excepto una.";
            default:
                return "";
        }
    }



    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView gameNameTextView;
        TextView gameObjectiveTextView;
        ImageView gameIconImageView;
        OnGameClickListener onGameClickListener;

        public ViewHolder(@NonNull View itemView, OnGameClickListener listener) {
            super(itemView);
            gameNameTextView = itemView.findViewById(R.id.gameNameTextView);
            gameObjectiveTextView = itemView.findViewById(R.id.gameObjectiveTextView);
            gameIconImageView = itemView.findViewById(R.id.gameIconImageView);
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
