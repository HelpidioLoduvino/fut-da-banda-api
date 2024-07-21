package com.example.futdabandaapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    private String position;

    private String gender;

    @Column(columnDefinition = "LONGTEXT")
    private String biography;

    public Player(Long id, String fullName, String email, String password, String userRole, String photo, String position, String gender, String biography) {
        super(id, fullName, email, password, userRole);
        this.photo = photo;
        this.position = position;
        this.gender = gender;
        this.biography = biography;
    }
}
