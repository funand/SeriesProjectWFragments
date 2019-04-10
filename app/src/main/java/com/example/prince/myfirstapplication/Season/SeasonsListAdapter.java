package com.example.prince.myfirstapplication.Season;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.prince.myfirstapplication.R;
import java.util.List;

public class SeasonsListAdapter extends RecyclerView.Adapter<SeasonsListAdapter.SeasonViewHolder> implements View.OnClickListener {
    List<Season> dataset;
    private View.OnClickListener listener;

    public SeasonsListAdapter(List<Season> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public SeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seasonlayout, parent, false);
        v.setOnClickListener(this);
        SeasonViewHolder svh  =new SeasonViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SeasonViewHolder seasonViewHolder, int position) {
        final Season season = dataset.get(position);

        seasonViewHolder.left.setText(season.getDesc());
        seasonViewHolder.name.setText("Season " + season.getNumber());
        seasonViewHolder.rating.setText(season.getCaps());
        seasonViewHolder.progressBar.setProgress(season.getBar());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    class SeasonViewHolder extends RecyclerView.ViewHolder {
        View layout;
        TextView  name, rating, left;
        ProgressBar progressBar;

        public SeasonViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.seasonAt);
            name = itemView.findViewById(R.id.seasonName);
            rating = itemView.findViewById(R.id.seasonRating);
            left = itemView.findViewById(R.id.seasonsLeft);
            progressBar = itemView.findViewById(R.id.seasonProgress);
        }
    }

}
