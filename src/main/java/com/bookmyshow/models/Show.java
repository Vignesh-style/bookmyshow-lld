package com.bookmyshow.models;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Show {
    private final long id;
    private final long movieId;
    private final String screen;
    private final LocalDateTime startTime;
    private final double pricePerSeat;
    private final Map<String, Boolean> seats = new ConcurrentHashMap<>();

    public Show(long id, long movieId, String screen, LocalDateTime startTime, double pricePerSeat, int totalSeats) {
        this.id = id; this.movieId = movieId; this.screen = screen; this.startTime = startTime; this.pricePerSeat = pricePerSeat;
        for (int i = 1; i <= totalSeats; i++) seats.put("A" + i, false);
    }
    public long getId() { return id; }
    public double getPricePerSeat() { return pricePerSeat; }
    public Map<String, Boolean> getSeats() { return seats; }
}
