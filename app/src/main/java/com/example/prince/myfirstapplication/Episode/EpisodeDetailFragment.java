package com.example.prince.myfirstapplication.Episode;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prince.myfirstapplication.R;
import com.example.prince.myfirstapplication.SQLiteConnection;
import com.example.prince.myfirstapplication.Season.SeriesInterfaceJSON;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link EpisodeListActivity}
 * in two-pane mode (on tablets) or a {@link EpisodeDetailActivity}
 * on handsets.
 */
public class EpisodeDetailFragment extends Fragment {
    private static final String Base_URL ="http://www.omdbapi.com/";
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Episode mItem;
    private String id;
    private String title;
    private String img;
    public static String imgurl;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EpisodeDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("season")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = new Episode("test","date","desc",1,R.drawable.ic_check_black_24dp,null);
            title = getArguments().getString("season");
            img = getArguments().getString("episode");
            id = getArguments().getString("imdbID");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.episode_overview_details, container, false);

        if (mItem != null) {

            final SQLiteConnection dbConection = new SQLiteConnection(getActivity(),"db_episodes",null,6);

            if(title.equals("0")){
                SQLiteDatabase db = dbConection.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM episodes WHERE imdbID = \""+id+"\"",null);
                cursor.moveToNext();
                System.out.println("name: "+cursor.getString(0));

                ((TextView) rootView.findViewById(R.id.episodeName)).setText(cursor.getString(0));
                ((TextView) rootView.findViewById(R.id.episodeInformation)).setText(cursor.getString(8));
                ((TextView) rootView.findViewById(R.id.episodeRelease)).setText("Released on " + cursor.getString(1));
                ((TextView) rootView.findViewById(R.id.episode_writer)).setText(cursor.getString(9));
                ((TextView) rootView.findViewById(R.id.episode_guest)).setText(cursor.getString(10));
                ((TextView) rootView.findViewById(R.id.episode_rate_it)).setText(cursor.getString(12) + "/10");
                ((TextView) rootView.findViewById(R.id.episode_votes)).setText(cursor.getString(13) + " votes");
                byte[] byteArray = cursor.getBlob(7);
                Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0 ,byteArray.length);
                ((ImageView) rootView.findViewById(R.id.episodeImage)).setImageBitmap(bm);
                View.OnClickListener delete = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SQLiteDatabase db = dbConection.getWritableDatabase();
                        db.execSQL("DELETE FROM episodes WHERE imdbID = \""+id+"\"");
                        Toast.makeText(getActivity(), "Episode deleted from Favorites", Toast.LENGTH_SHORT).show();
                    }
                };
                rootView.findViewById(R.id.skip).setOnClickListener(delete);
            }
            else {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SeriesInterfaceJSON.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                SeriesInterfaceJSON api = retrofit.create(SeriesInterfaceJSON.class);
                Call<Episode> call = api.getEpisodes(title, img);
                call.enqueue(new Callback<Episode>() {
                    @Override
                    public void onResponse(Call<Episode> call, Response<Episode> response) {
                        final Episode ser = response.body();
                        imgurl = ser.getPoster();
                        System.out.println("name: "+ser.getName());

                        Picasso.get().load(imgurl).fit().into((ImageView) rootView.findViewById(R.id.episodeImage));
                        ((TextView) rootView.findViewById(R.id.episodeName)).setText(ser.getName());
                        ((TextView) rootView.findViewById(R.id.episodeInformation)).setText(ser.getPlot());
                        ((TextView) rootView.findViewById(R.id.episodeRelease)).setText("Released on " + ser.getDate());
                        ((TextView) rootView.findViewById(R.id.episode_writer)).setText(ser.getWriter());
                        ((TextView) rootView.findViewById(R.id.episode_guest)).setText(ser.getActors());
                        ((TextView) rootView.findViewById(R.id.episode_rate_it)).setText(ser.getImdbRating() + "/10");
                        ((TextView) rootView.findViewById(R.id.episode_votes)).setText(ser.getImdbVotes() + " votes");


                        View.OnClickListener ocl = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = dbConection.getWritableDatabase();
                                //db.rawQuery("DROP TABLE episodes",null);
                                final ContentValues values = new ContentValues();
                                values.put("title", ser.getName());
                                values.put("date", ser.getDate());
                                values.put("imdbRating", ser.getImdbRating());
                                values.put("num", ser.getNum());
                                values.put("season",title);
                                values.put("episode",img);
                                values.put("imdbID",ser.getImdbID());
                                values.put("plot",ser.getPlot());
                                values.put("writers",ser.getWriter());
                                values.put("actors",ser.getActors());
                                values.put("runtime",ser.getRuntime());
                                values.put("rating",ser.getImdbRating());
                                values.put("votes",ser.getImdbVotes());
                                final RequestCreator pic = Picasso.get().load(imgurl);
                                final ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                final Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Bitmap bm = pic.get();

                                            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                            byte[] img = bos.toByteArray();
                                            values.put("image",img);

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Long idRes = db.insert("episodes", "num", values);
                                Toast.makeText(getActivity(), "Episode added to Favorites", Toast.LENGTH_SHORT).show();
                                Cursor cursor = db.rawQuery("SELECT * FROM episodes", null);
                                while (cursor.moveToNext()) {
                                    System.out.println(cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + cursor.getInt(3));
                                }
                            }
                        };
                        rootView.findViewById(R.id.collection).setOnClickListener(ocl);
                        View.OnClickListener delete = new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SQLiteDatabase db = dbConection.getWritableDatabase();
                                System.out.println("imdb      "+ser.getImdbID());
                                db.execSQL("DELETE FROM episodes WHERE imdbID = \""+ser.getImdbID()+"\"");
                                Toast.makeText(getActivity(), "Episode deleted from Favorites", Toast.LENGTH_SHORT).show();
                            }
                        };
                        rootView.findViewById(R.id.skip).setOnClickListener(delete);

                    }

                    @Override
                    public void onFailure(Call<Episode> call, Throwable t) {
                        System.out.println("failed");

                    }
                });

            }

            ((ImageView) rootView.findViewById(R.id.watched)).setImageResource(R.drawable.ic_check_black_24dp);
            ((ImageView) rootView.findViewById(R.id.collection)).setImageResource(R.drawable.ic_turned_in_not_black_24dp);
            ((ImageView) rootView.findViewById(R.id.skip)).setImageResource(R.drawable.ic_skip_next_black_24dp);
            ((ImageView) rootView.findViewById(R.id.play)).setImageResource(R.drawable.ic_play_arrow_black_24dp);

             rootView.findViewById(R.id.starred).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ImageView) rootView.findViewById(R.id.starred)).setImageResource(R.drawable.ic_star_blacked_24dp);
                    Toast.makeText(getActivity(), "Episode starred", Toast.LENGTH_SHORT).show();

                }
            });

        }
        return rootView;
    }
}
