package com.best.workshop.drinkee.services.impl;

import com.best.workshop.drinkee.dtos.Drink;
import com.best.workshop.drinkee.dtos.Ingredient;
import com.best.workshop.drinkee.dtos.collections.DrinksCollection;
import com.best.workshop.drinkee.services.DrinkService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class DrinkServiceImpl implements DrinkService {
    String BASE_URL = "https://thecocktaildb.com/api/json/v1/1/";
    private static final String FILTER_PATH = "/filter.php?i=";


    private final RestTemplate restTemplate;

    public DrinkServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Drink> getDrinksByIngredient(Ingredient ingredient) {
        DrinksCollection drinksList = this.restTemplate.getForObject(BASE_URL + FILTER_PATH + ingredient.getName(), DrinksCollection.class);
        return drinksList != null ? drinksList.getDrinks() : Collections.emptyList();
    }
}
