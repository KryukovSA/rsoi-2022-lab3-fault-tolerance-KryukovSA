package com.example.reservationservice.service;

import com.example.request1.requests.ReturnBook;
import com.example.request1.requests.TakeBook;
import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.model.Status;
import com.example.reservationservice.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.UUID.randomUUID;

@AllArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    //вернутть унигу
    public ResponseEntity<?> returnBook(String username, UUID reservationUid, ReturnBook returnBookRequest) {
        Reservation reservation = reservationRepository.findByReservationUid(reservationUid);
        RestTemplate restTemplate = new RestTemplate();
        if(reservation == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No reservation found for " + reservationUid.toString());
        Boolean expired = false, badCondition = false;
        if (returnBookRequest.getDate().after(reservation.getTillDate())) {
            reservation.setStatus(Status.EXPIRED);
            expired = true;
        } else {
            reservation.setStatus(Status.RETURNED);
        }
        if(!returnBookRequest.getCondition().equals("EXCELLENT")) badCondition = true;
        String url = "";
        if(expired || badCondition) {
            url = "http://gateway:8080/api/v1/rating" + "/decrease" + "?username=" + username + "&expired=" + expired + "&badCondition=" + badCondition;
        } else {
            url = "http://gateway:8080/api/v1/rating" + "/increase" + "?username=" + username;
        }
        restTemplate.postForLocation(url, null);
        //ToDo increase available_count
        reservationRepository.save(reservation);
        return ResponseEntity.noContent().build();
    }

    // взять книгу
    public ResponseEntity<Reservation> takeBook(String username, TakeBook takeBookRequest) {

        Reservation reservation = new Reservation();
        reservation.setReservationUid(randomUUID());
        reservation.setBookUid(takeBookRequest.getBookUid());
        reservation.setLibraryUid(takeBookRequest.getLibraryUid());
        reservation.setStartDate(new Date());
        reservation.setTillDate(takeBookRequest.getTillDate());
        reservation.setUsername(username);
        reservation.setStatus(Status.RENTED);
        reservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(reservation);
    }
    //Получить информацию по всем взятым в прокат книгам пользователя
    public ResponseEntity<List<Reservation>> getUserBooksInfo(String username) {
        List<Reservation> reservation = reservationRepository
                .findAllByUsernameAndStatusIn(username, new HashSet<>(Arrays.asList(Status.EXPIRED, Status.RENTED)));
        return ResponseEntity.ok(reservation);
    }

}
