package com.example.reservationservice.controller;

import com.example.request1.requests.ReturnBook;
import com.example.request1.requests.TakeBook;
import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationController {
    ReservationService reservationService;

//    @GetMapping
//    public ResponseEntity<?> getUserReservedBooks(@RequestHeader("X-User-Name") String username) {
//        return ResponseEntity.ok(reservationService.getUserBooksInfo(username));
//    }

    @GetMapping(value = "")
    public ResponseEntity<List<Reservation>> getUserBooksInfo(@RequestParam("username") String username) {
        return reservationService.getUserBooksInfo(username);
    }

    @PostMapping(value = "")
    public ResponseEntity<Reservation> takeBook(@RequestParam("username") String username,
                                                @RequestBody TakeBook takeBookRequest) {
        return reservationService.takeBook(username, takeBookRequest);
    }

    @PostMapping(value = "/{reservationUid}/return")
    public ResponseEntity<?> returnBook(@RequestParam("username") String username,
                                      @PathVariable("reservationUid") UUID reservationUid,
                                      @RequestBody ReturnBook returnBookRequest) {
        return reservationService.returnBook(username, reservationUid, returnBookRequest);
    }

}
