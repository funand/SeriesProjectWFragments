package com.example.prince.myfirstapplication.Episode;

import com.google.gson.annotations.SerializedName;


public class Episode {

    @SerializedName("Title")
    private String name;
    @SerializedName("Released")
    private String date;
    private String imdbID;
    private String Runtime;
    private String Actors;
    private String Writer;
    private String desc;
    @SerializedName("Episode")
    private int num;
    private int images;
    private String Poster;
    private String Plot;
    private String imdbRating;
    private String imdbVotes;
    @SerializedName("Season")
    private String season;

    public Episode(String name, String date, String desc,int num, int images, String imgurl) {
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.num = num;
        this.images = images;
        this.Poster = imgurl;
    }

    public String getPlot() {
        return Plot;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public String getName() {
        return name;
    }

    public int getImgages() {
        return images;
    }

    public void setImages(int imgres) {
        this.images = imgres;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRuntime() {
        return Runtime;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public String getActors() {
        return Actors;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getWriter() {
        return Writer;
    }

    public void setWriter(String writer) {
        Writer = writer;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
