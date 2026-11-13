package com.bookmyshow.controller;

import com.bookmyshow.models.Booking;
import com.bookmyshow.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/book")
    public Booking bookSeats(@RequestParam long userId, @RequestParam long showId, @RequestBody List<String> seats) {
        return service.bookSeats(userId, showId, seats);
    }

    @GetMapping
    public List<Booking> getAllBookings() {
        return service.getAllBookings();
    }

    @PostMapping("/{bookingId}/cancel")
    public String cancelBooking(@PathVariable long bookingId) {
        boolean result = service.cancelBooking(bookingId);
        return result ? "Booking cancelled successfully" : "Failed to cancel booking";
    }
}
