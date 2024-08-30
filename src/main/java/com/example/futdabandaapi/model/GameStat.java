package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Club club;

    private Integer fouls;

    private Integer goals;

    private Integer corners;

    private Integer yellowCards;

    private Integer redCards;

    private Integer blueCards;

    @PrePersist
    @PreUpdate
    protected void initializeFields() {
        if (goals == null) {
            goals = 0;
        }
        if (fouls == null) {
            fouls = 0;
        }
        if (corners == null) {
            corners = 0;
        }
        if (yellowCards == null) {
            yellowCards = 0;
        }
        if (redCards == null) {
            redCards = 0;
        }
        if (blueCards == null) {
            blueCards = 0;
        }
    }

    public void addGoal() {
        if (this.goals == null) {
            this.goals = 0;
        }
        this.goals += 1;
    }

    public void addFouls() {
        if (this.fouls == null) {
            this.fouls = 0;
        }
        this.fouls += 1;
    }

    public void addYellowCard() {
        if (this.yellowCards == null) {
            this.yellowCards = 0;
        }
        this.yellowCards += 1;
    }

    public void addRedCard() {
        if (this.redCards == null) {
            this.redCards = 0;
        }
        this.redCards += 1;
    }

    public void addBlueCard() {
        if (this.blueCards == null) {
            this.blueCards = 0;
        }
        this.blueCards += 1;
    }

    public void addCorner() {
        if (this.corners == null) {
            this.corners = 0;
        }
        this.corners += 1;
    }

    public void removeGoal() {
        this.goals -= 1;
    }

    public void removeFouls() {
        this.fouls -= 1;
    }

    public void removeYellowCard() {
        this.yellowCards -= 1;
    }

    public void removeRedCard() {
        this.redCards -= 1;
    }

    public void removeBlueCard() {
        this.blueCards -= 1;
    }

    public void removeCorner() {
        this.corners -= 1;
    }

}
