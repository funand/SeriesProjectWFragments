package com.example.prince.myfirstapplication.Episode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prince.myfirstapplication.R;
import com.example.prince.myfirstapplication.SQLiteConnection;
import com.example.prince.myfirstapplication.Season.Season;
import com.example.prince.myfirstapplication.Season.SeriesInterfaceJSON;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EpisodeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EpisodeListActivity extends Activity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // horizontal layouts (res-layout-land).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        getItems();

    }

    private void getItems(){
        Intent i = getIntent();
        if(i.getIntExtra("season",0) == 0){
            SQLiteConnection conn = new SQLiteConnection(this,"db_episodes",null,6);
            SQLiteDatabase db = conn.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM episodes",null);
            ArrayList<Episode> episodes = new ArrayList<>();
            while(cursor.moveToNext()){
                Episode next = new Episode(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), R.drawable.ic_check_black_24dp, null);
                next.setImdbID(cursor.getString(6));
                episodes.add(next);
            }
            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;

            setupRecyclerView((RecyclerView) recyclerView, episodes, "0");
        }
        else{
            final int num = i.getIntExtra("season", 0);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SeriesInterfaceJSON.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SeriesInterfaceJSON api = retrofit.create(SeriesInterfaceJSON.class);
            Call<Season> call = api.getSeason(Integer.toString(num));
            call.enqueue(new Callback<Season>() {
                @Override
                public void onResponse(Call<Season> call, Response<Season> response) {
                    Season season = response.body();
                    final ArrayList<Episode> episodes = new ArrayList<>();
                    for (int i = 1; i <= season.getEpisodes().size(); i++) {
                        Season.Series_Episode next = season.getEpisodes().get(i - 1);
                        episodes.add(new Episode(next.getTitle(), next.getReleased(), next.getImdbRating(), i, R.drawable.ic_check_black_24dp, null));
                    }

                    View recyclerView = findViewById(R.id.item_list);
                    assert recyclerView != null;
                    setupRecyclerView((RecyclerView) recyclerView, episodes, Integer.toString(season.getNumber()));
                }

                @Override
                public void onFailure(Call<Season> call, Throwable t) {
                    System.out.println("error ------> " + t.getMessage());
                }
            });
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Episode> items, String season) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, items, mTwoPane, season));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.EpisodeViewHolder> {

        private final EpisodeListActivity mParentActivity;
        private final List<Episode> mValues;
        private final boolean mTwoPane;
        private final String season;


        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Episode item = (Episode) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString("season", season);
                    arguments.putString("episode", Integer.toString(item.getNum()));
                    arguments.putString("imdbID",item.getImdbID());
                    EpisodeDetailFragment fragment = new EpisodeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                }
                else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EpisodeDetailActivity.class);
                    intent.putExtra("season", season);
                    intent.putExtra("episode", Integer.toString(item.getNum()));
                    intent.putExtra("imdbID",item.getImdbID());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(EpisodeListActivity parent,
                                      List<Episode> items,
                                      boolean twoPane, String season) {
            this.mValues = items;
            this.mParentActivity = parent;
            this.mTwoPane = twoPane;
            this.season = season;
        }

        public EpisodeViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodelayout, parent, false);
            EpisodeViewHolder svh = new EpisodeViewHolder(v);
            return svh;
        }

        @Override
        public void onBindViewHolder(final EpisodeViewHolder holder, final int position) {

            final Episode episode = mValues.get(position);
            holder.episodeTitle.setText(episode.getName());
            holder.episodeDate.setText(episode.getDate());
//            holder.episodeDesc.setText(s.getDesc());
            holder.episodeNumber.setText(Integer.toString(episode.getNum()));
//            holder.episodeImg.setImageResource(R.drawable.ic_check_black_24dp);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class EpisodeViewHolder extends RecyclerView.ViewHolder {
            View layout;
            TextView episodeTitle, episodeDate, episodeNumber;
            ImageView episodeImg;

            EpisodeViewHolder(@NonNull View itemView) {
                super(itemView);
                layout = itemView.findViewById(R.id.epis);
                episodeTitle = itemView.findViewById(R.id.episodeTitle);
                episodeNumber = itemView.findViewById(R.id.episodeNumber);
                episodeDate = itemView.findViewById(R.id.episodeDate);
                episodeImg = itemView.findViewById(R.id.imageView4);
            }
        }
    }
}
