package com.example.prince.myfirstapplication.Season;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Season {
    @SerializedName("Season")
    private int number;
    private String desc;
    private String caps;
    private int bar;
    private String seriesID;
    private List<Series_Episode> Episodes;

    public Season(int number, String desc, String caps, int bar,String seriesID) {
        this.number = number;
        this.desc = desc;
        this.caps = caps;
        this.bar = bar;
        this.seriesID = seriesID;
        this.Episodes = new ArrayList<>();
    }

    public class Series_Episode{
        private String Title;
        private String Released;
        private int Episode;
        private String imdbRating;
        private String imdbID;

        public Series_Episode(String title, String released, int episode, String imdbRating, String imdbID) {
            Title = title;
            Released = released;
            Episode = episode;
            this.imdbRating = imdbRating;
            this.imdbID = imdbID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getReleased() {
            return Released;
        }

        public void setReleased(String released) {
            Released = released;
        }

        public int getEpisode() {
            return Episode;
        }

        public void setEpisode(int episode) {
            Episode = episode;
        }

        public String getImdbRating() {
            return imdbRating;
        }

        public void setImdbRating(String imdbRating) {
            this.imdbRating = imdbRating;
        }

        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCaps() {
        return caps;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(String seriesID) {
        this.seriesID = seriesID;
    }

    public List<Series_Episode> getEpisodes() {
        return Episodes;
    }

    public void setEpisodes(List<Series_Episode> episodes) {
        this.Episodes = episodes;
    }
}
