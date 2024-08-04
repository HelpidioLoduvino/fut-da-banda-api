package com.example.futdabandaapi.entities;

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

    private String badge;

    private String state;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String groupType;

    private String category;

    private boolean isReadyToPlay;

    @ManyToMany
    private List<User> players = new ArrayList<>();

}
