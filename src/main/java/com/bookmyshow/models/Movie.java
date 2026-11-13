package com.bookmyshow.models;

public class Movie {
    private final long id;
    private final String title;
    private final String language;
    private final String genre;
    public Movie(long id, String title, String language, String genre) {
        this.id = id; this.title = title; this.language = language; this.genre = genre;
    }
    public long getId() { return id; }
    public String getTitle() { return title; }
}