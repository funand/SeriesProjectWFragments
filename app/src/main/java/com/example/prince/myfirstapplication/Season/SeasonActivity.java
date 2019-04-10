package com.example.prince.myfirstapplication.Season;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.example.prince.myfirstapplication.Episode.EpisodeListActivity;
import com.example.prince.myfirstapplication.Overview;
import com.example.prince.myfirstapplication.R;
import com.example.prince.myfirstapplication.Series;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SeasonActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Season> dataset;
    LinearLayoutManager layoutManager;
    CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seasonrecycle);

        mCompositeDisposable = new CompositeDisposable();

        initRecyclerView();
        loadJSON();
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.season_recycle_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void loadJSON(){
        dataset = new ArrayList<Season>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SeriesInterfaceJSON.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SeriesInterfaceJSON api = retrofit.create(SeriesInterfaceJSON.class);
        Call<Series> call = api.getSeries();
        call.enqueue(new Callback<Series>() {
            @Override
            public void onResponse(Call<Series> call, Response<Series> response) {
                Series ser = response.body();
                for(int i=1;i<=ser.getTotalSeasons();i++){
                    dataset.add(new Season(i,"All released","6/10",60,ser.getImdbID()));
                }

                SeasonsListAdapter dataAdapter = new SeasonsListAdapter(dataset);


                dataAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), EpisodeListActivity.class);
                        intent.putExtra("season",  dataset.get(recyclerView.getChildAdapterPosition(view)).getNumber());
                        intent.putExtra("seriesID", dataset.get(recyclerView.getChildAdapterPosition(view)).getSeriesID());
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<Series> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToEpisodes(View view){
        Intent i = new Intent(this, Overview.class);
        startActivity(i);
    }
    public void goToFavorites(View view){
        Intent i = new Intent(this,EpisodeListActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
