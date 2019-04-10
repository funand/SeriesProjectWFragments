package com.example.prince.myfirstapplication.Season;
import com.example.prince.myfirstapplication.Episode.Episode;
import com.example.prince.myfirstapplication.Series;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SeriesInterfaceJSON {
    String BASE_URL ="http://www.omdbapi.com/";
    @GET("?apikey=b4d74009&i=tt0203259") Call<Series> getSeries();
    @GET("?apikey=b4d74009&i=tt0203259") Call<Season> getSeason(@Query("season") String season);
    @GET("?apikey=b4d74009&i=tt0203259") Call<Episode> getEpisodes(@Query("season") String season, @Query("episode") String episode);
}
//tt3281796