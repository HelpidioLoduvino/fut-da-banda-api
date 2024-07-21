package com.example.futdabandaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String abv;

    private String province;

    private String badge;

    private String state;

    private String description;

    private String cGroup;

    private String category;

    private boolean isReadyToPlay;

    @ManyToOne
    private User user;

}
