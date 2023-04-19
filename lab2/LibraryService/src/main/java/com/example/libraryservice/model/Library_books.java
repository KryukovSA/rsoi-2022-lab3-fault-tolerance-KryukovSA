package com.example.libraryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "library_books")
@Getter
@Setter
public class Library_books {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "INT REFERENCES books (id)")
    private Integer bookId;

    @Column(columnDefinition = "INT REFERENCES library (id)")
    private Integer libraryId;

    @NotNull
    private  Integer availableCount;

}