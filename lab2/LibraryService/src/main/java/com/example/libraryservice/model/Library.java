package com.example.libraryservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "library")
@Getter
@Setter
public class Library {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private UUID libraryUid;

    @Column(length = 80)
    @NotNull
    private String name;

    @Column(length = 255)
    @NotNull
    private String city;

    @Column(length = 80)
    @NotNull
    private String address;

}
