package com.best.workshop.drinkee.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Ingredient {
    private final String name;

    @JsonCreator
    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
