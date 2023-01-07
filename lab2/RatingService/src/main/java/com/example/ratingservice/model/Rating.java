package com.example.ratingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "rating")
@Getter
@Setter
public class Rating {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 80)
    @NotNull
    private String username;

    @Column(columnDefinition = "INT NOT NULL CHECK (stars BETWEEN 0 AND 100)")
    private Integer stars;

}
