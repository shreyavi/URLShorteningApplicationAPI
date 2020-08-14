package com.example.URLShorteningAPI.model;

import javax.persistence.*;
import java.net.URL;

@Entity(name ="urlinfo")
@Table(name = "urlinfo")
public class URLInfo {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private URL longURL;

    @Column(nullable = false)
    private int clickCount;

    @Column(nullable = false)
    private Boolean isActive;

    public URLInfo(){
    }

    public URLInfo(String id, URL longURL, int clickCount, Boolean isActive){
        this.id = id;
        this.longURL = longURL;
        this.clickCount = clickCount;
        this.isActive = isActive;
    }

    public String getID(){
        return this.id;
    }

    public void setID(String id){
        this.id =id;
    }

    public URL getLongURL(){
        return this.longURL;
    }

    public void setLongURL(URL url){
        this.longURL = url;
    }

    public int getClickCount(){
        return this.clickCount;
    }

    public void setClickCount(int clickCount){
        this.clickCount = clickCount;
    }

    public Boolean isActive() { return isActive; }

    public void setActive(Boolean active) {
        isActive = active;
    }
}