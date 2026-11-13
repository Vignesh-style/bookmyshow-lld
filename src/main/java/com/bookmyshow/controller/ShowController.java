package com.bookmyshow.controller;

import com.bookmyshow.service.BookingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Stack;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    private final BookingService service;

    public ShowController(BookingService service) {
        this.service = service;
    }

    @GetMapping("/{showId}/available-seats")
    public List<String> getAvailableSeats(@PathVariable long showId) {
        return service.getAvailableSeats(showId);

    }
}
