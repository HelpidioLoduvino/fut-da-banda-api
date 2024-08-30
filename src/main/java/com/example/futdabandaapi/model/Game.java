package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Championship championship;

    @ManyToOne
    private Field field;

    @ManyToOne
    private Club firstClub;

    @ManyToOne
    private Club secondClub;

    private Integer matchDay;

    private LocalDateTime dateTime;

    private String status;

}
