package com.example.URLShorteningAPI.dto;

import java.net.URL;

public class ShortURL {
    private URL shortURL;

    public ShortURL(URL shortURL) {
        this.shortURL = shortURL;
    }

    public URL getShortURL() {
        return shortURL;
    }

    public void setShortURL(URL shortURL) {
        this.shortURL = shortURL;
    }
}