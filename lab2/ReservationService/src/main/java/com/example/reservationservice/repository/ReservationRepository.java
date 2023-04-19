package com.example.reservationservice.repository;

import com.example.reservationservice.model.Reservation;
import com.example.reservationservice.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByReservationUid(UUID uuid);
    List<Reservation> findAllByUsernameAndStatusIn(String username, Set<Status> statuses);
}
