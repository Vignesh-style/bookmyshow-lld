package com.bookmyshow.models;

import java.util.List;

public class Booking {

    private final long id;
    private final long userId;
    private final long showId;
    private final List<String> seatNumbers;
    private final double totalAmount;
    private BookingStatus status;

    public Booking(long id, long userId, long showId, List<String> seatNumbers, double totalAmount) {
        this.id = id; this.userId = userId; this.showId = showId;
        this.seatNumbers = seatNumbers; this.totalAmount = totalAmount;
        this.status = BookingStatus.BOOKED;
    }

    public void cancel() { this.status = BookingStatus.CANCELLED; }
    public BookingStatus getStatus() { return status; }
    public long getId() { return id; }

    public long getShowId() {
        return showId;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public long getUserId() {
        return userId;
    }
}
