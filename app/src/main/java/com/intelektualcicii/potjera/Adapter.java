package com.intelektualcicii.potjera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList <String> gameList;
    int counter=1;

    public Adapter(ArrayList <String> gameList) {
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_user_results,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.score_GameResult.setText("SCORE: "+gameList.get(position));
        holder.gameNo_GameResult.setText("gameNo: "+counter+".");
        counter++;
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView score_GameResult, gameNo_GameResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            score_GameResult=itemView.findViewById(R.id.tv_score_GameResult);
            gameNo_GameResult=itemView.findViewById(R.id.gameno_GameResult);
        }
    }
}
