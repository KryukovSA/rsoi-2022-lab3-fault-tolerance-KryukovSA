package com.example.gateway.controller;

import com.example.libraryservice.model.*;
import com.example.libraryservice.model.Library;
import com.example.ratingservice.model.Rating;
import com.example.request1.requests.ReturnBook;
import com.example.request1.requests.TakeBook;
import com.example.reservationservice.model.Reservation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/api/v1/reservations")
public class ReservationServiceController {
    public static final String reservation_url = "http://reservation:8070/api/v1/reservations";
    Reservation mainReservation;
    @PostMapping
    public ResponseEntity<HashMap> takeBook(@RequestHeader("X-User-Name") String username,
                                      @RequestBody TakeBook takeBookRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TakeBook> request = new HttpEntity<>(takeBookRequest, null);
        Reservation result = restTemplate.postForObject(reservation_url + "?username=" + username, request, Reservation.class);
        mainReservation = result;
        Books book = restTemplate.getForObject("http://library:8060/api/v1/libraries/getBook" + "?libraryUid=" + result.getLibraryUid() + "&bookUid=" + result.getBookUid(), Books.class);
        HashMap<String, Object> book1 = new HashMap<>();
        book1.put("bookUid", book.getBookUid());
        book1.put("name", book.getName());
        book1.put("author", book.getAuthor());
        book1.put("genre", book.getGenre());

        Library lib = restTemplate.getForObject("http://library:8060/api/v1/libraries/getLib" + "?libraryUid=" + result.getLibraryUid(), Library.class);
        HashMap<String, Object> lib1 = new HashMap<>();
        lib1.put("libraryUid", lib.getLibraryUid());
        lib1.put("name", lib.getName());
        lib1.put("address", lib.getAddress());
        lib1.put("city", lib.getCity());

        Integer rating = restTemplate.getForObject("http://rating:8050/api/v1/rating" + "?username=" + result.getUsername(), Integer.class);
        HashMap<String, Integer> raiting = new HashMap<>();
        raiting.put("stars", rating);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        HashMap<String, Object> output = new HashMap<>();
        output.put("reservationUid", result.getReservationUid());
        output.put("status", result.getStatus());
        output.put("startDate", df.format(result.getStartDate()));
        output.put("tillDate", df.format(result.getTillDate()));
        output.put("book", book1);
        output.put("library", lib1);
        output.put("rating", raiting);

        return ResponseEntity.ok(output);
    }

    @GetMapping
    public ResponseEntity<?> getUserReservedBooks(@RequestHeader("X-User-Name") String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = reservation_url + "?username=" + username;
        List<Reservation> result = new ArrayList<Reservation>();
        result = restTemplate.getForObject(url, List.class);

        List<HashMap<String, Object>> answer = new ArrayList<>();


        Books book = restTemplate.getForObject("http://library:8060/api/v1/libraries/getBook" + "?libraryUid=" + mainReservation.getLibraryUid() + "&bookUid=" + mainReservation.getBookUid(), Books.class);//result.get(0)
        HashMap<String, Object> book1 = new HashMap<>();
        book1.put("bookUid", book.getBookUid());
        book1.put("name", book.getName());
        book1.put("author", book.getAuthor());
        book1.put("genre", book.getGenre());

        Library lib = restTemplate.getForObject("http://library:8060/api/v1/libraries/getLib" + "?libraryUid=" + mainReservation.getLibraryUid(), Library.class);
        HashMap<String, Object> lib1 = new HashMap<>();
        lib1.put("libraryUid", lib.getLibraryUid());
        lib1.put("name", lib.getName());
        lib1.put("address", lib.getAddress());
        lib1.put("city", lib.getCity());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> output = new HashMap<>();
        output.put("reservationUid", mainReservation.getReservationUid());
        output.put("status", mainReservation.getStatus());
        output.put("startDate", df.format(mainReservation.getStartDate()));
        output.put("tillDate", df.format(mainReservation.getTillDate()));
        output.put("book", book1);
        output.put("library", lib1);

        answer.add(output);

        return ResponseEntity.ok(answer);
    }




    @PostMapping("/{reservationUid}/return")
    public ResponseEntity<?> returnBook(@PathVariable("reservationUid") UUID reservationUid,
                                        @RequestHeader("X-User-Name") String username,
                                        @RequestBody ReturnBook returnBookRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ReturnBook> request = new HttpEntity<>(returnBookRequest, null);
        try {
        return restTemplate.postForEntity(reservation_url + "/" + reservationUid + "/return" + "?username=" + username, request, ReturnBook.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.badRequest().body(e.getResponseBodyAsString());
        }
    }

}
