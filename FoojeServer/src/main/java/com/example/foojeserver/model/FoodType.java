package com.example.foojeserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "food_type")
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_TYPE_ID")
    private long FOOD_TYPE_ID;

    private String NAME;

    public long getFOOD_TYPE_ID() {
        return FOOD_TYPE_ID;
    }

    public void setFOOD_TYPE_ID(long FOOD_TYPE_ID) {
        this.FOOD_TYPE_ID = FOOD_TYPE_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
