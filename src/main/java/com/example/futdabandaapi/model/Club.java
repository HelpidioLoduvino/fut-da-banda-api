package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String abv;

    private String province;

    private String emblem;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String competition;

    private String category;

    private String admissionTest;

    private String gender;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

    private String ban;

}
