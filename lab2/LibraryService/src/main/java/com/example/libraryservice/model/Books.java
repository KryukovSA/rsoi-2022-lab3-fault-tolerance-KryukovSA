package com.example.libraryservice.model;


import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "books")
@Getter
@Setter
public class Books {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private UUID bookUid;

    @Column
    @NotNull
    private String name;

    @Column
    private String author;

    @Column
    private String genre;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20) DEFAULT 'EXCELLENT' CHECK (condition IN ('EXCELLENT', 'GOOD', 'BAD')) ")
    private Condition condition = Condition.EXCELLENT;;

}
