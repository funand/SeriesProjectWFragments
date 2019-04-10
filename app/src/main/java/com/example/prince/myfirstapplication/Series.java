package com.example.prince.myfirstapplication;

public class Series {
        private String Title;
        private int totalSeasons;
        private String imdbID;

        public Series(String title, int totalSeasons, String imdbID) {
            Title = title;
            this.totalSeasons = totalSeasons;
            this.imdbID = imdbID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public int getTotalSeasons() {
            return totalSeasons;
        }

        public void setTotalSeasons(int totalSeasons) {
            this.totalSeasons = totalSeasons;
        }

        public String getImdbID() {
            return imdbID;
        }

        public void setImdbID(String imdbID) {
            this.imdbID = imdbID;
        }
}
