package com.example.futdabandaapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    Player player;

    private Integer assists;

    private Integer fouls;

    private Integer goals;

    private Integer yellowCards;

    private Integer redCards;

    private Integer blueCards;

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

    public void addAssists(){
        if(assists == null){
            this.assists = 0;
        }
        this.assists +=1;
    }

    public void removeGoal() {
        if(this.goals > 0){
            this.goals -= 1;
        }
    }

    public void removeFouls() {
        if(this.fouls > 0){
            this.fouls -= 1;
        }
    }

    public void removeYellowCard() {
        if(this.yellowCards > 0){
            this.yellowCards -= 1;
        }
    }

    public void removeRedCard() {
        if(this.redCards > 0){
            this.redCards -= 1;
        }
    }

    public void removeBlueCard() {
        if(this.blueCards > 0){
            this.blueCards -= 1;
        }
    }

    public void removeAssist() {
        if(this.assists > 0){
            this.assists -= 1;
        }
    }

}
