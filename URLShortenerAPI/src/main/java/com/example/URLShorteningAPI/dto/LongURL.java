package com.example.URLShorteningAPI.dto;


public class LongURL {

        private String longURL;

        public LongURL() {
        }

        public LongURL(String longURL) {
            this.longURL = longURL;
        }

        public String getLongURL() {
            return longURL;
        }

        public void setLongURL(String longURL) {
            this.longURL = longURL;
        }

}
