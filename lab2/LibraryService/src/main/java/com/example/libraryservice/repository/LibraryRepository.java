package com.example.libraryservice.repository;

import com.example.libraryservice.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {
    List<Library> findAllByCity(String city);
    Library findByLibraryUid(UUID uuid);
}