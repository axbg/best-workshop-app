package com.best.workshop.drinkee.services;

import com.best.workshop.drinkee.dtos.Drink;
import com.best.workshop.drinkee.dtos.Ingredient;

import java.util.List;

public interface DrinkService {
    List<Drink> getDrinksByIngredient(Ingredient ingredient);
}
