package com.bookmyshow.service;

import com.bookmyshow.models.Booking;
import com.bookmyshow.models.Movie;
import com.bookmyshow.models.Show;
import com.bookmyshow.models.User;
import com.bookmyshow.repo.InMemoryRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookingService {
    private final InMemoryRepo<Movie> movieRepo = new InMemoryRepo<>();
    private final InMemoryRepo<User> userRepo = new InMemoryRepo<>();
    private final InMemoryRepo<Show> showRepo = new InMemoryRepo<>();
    private final InMemoryRepo<Booking> bookingRepo = new InMemoryRepo<>();

    private final Map<Long, ReentrantLock> showLocks = new ConcurrentHashMap<>();

    public BookingService() {
        // Seed data
        long m1 = movieRepo.save(new Movie(1, "Leo", "Tamil", "Action"));
        long s1 = showRepo.save(new Show(1, m1, "Screen-1",
                java.time.LocalDateTime.now().plusHours(2), 150.0, 10));
        long u1 = userRepo.save(new User(1, "Vignesh", "vignesh@zoho.com"));
        showLocks.put(s1, new ReentrantLock());
    }

    public List<String> getAvailableSeats(long showId) {
        Show show = showRepo.find(showId).orElseThrow();
        return show.getSeats().entrySet().stream()
                .filter(e -> !e.getValue())
                .map(Map.Entry::getKey)
                .toList();
    }

    public Booking bookSeats(long userId, long showId, List<String> seats) {
        Show show = showRepo.find(showId).orElseThrow(() -> new RuntimeException("Show not found"));
        User user = userRepo.find(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ReentrantLock lock = showLocks.get(showId);

        lock.lock();
        try {
            for (String seat : seats) {
                if (show.getSeats().getOrDefault(seat, true))
                    throw new RuntimeException("Seat already booked: " + seat);
            }
            seats.forEach(s -> show.getSeats().put(s, true));
            double total = show.getPricePerSeat() * seats.size();
            Booking booking = new Booking(bookingRepo.save(null), userId, showId, seats, total);
            bookingRepo.update(booking.getId(), booking);
            System.out.println("💳 Payment Successful: ₹" + total);
            return booking;
        } finally {
            lock.unlock();
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public boolean cancelBooking(long bookingId) {
        Booking booking = bookingRepo.find(bookingId).orElseThrow();
        booking.cancel();

        Show show = showRepo.find(booking.getShowId()).orElseThrow();

        ReentrantLock lock = showLocks.get(show);

        lock.lock();
        try {
            Map<String, Boolean> seats = show.getSeats();
            booking.getSeatNumbers().forEach(s -> seats.put(s, false));
            showRepo.save(show);
            return true;
        }
        finally {
                lock.unlock();
        }
    }
}
