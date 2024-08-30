package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Championship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String manualRule;

    private String category;

    private String province;

    private String type;

    private String gender;

    private String pricePer;

    private Double price;

    private String rule;

    private Integer matchDay;

    @ManyToMany
    private List<Club> clubs = new ArrayList<>();

    private String ban;

    private LocalDate expiryDate;

    private LocalDate startDate;

}
