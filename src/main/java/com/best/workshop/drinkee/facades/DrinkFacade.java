package com.best.workshop.drinkee.facades;

import com.best.workshop.drinkee.dtos.Drink;
import com.best.workshop.drinkee.dtos.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DrinkFacade {
    Map<String, List<Drink>> getDrinksByPictures(List<MultipartFile> pictures);
    Map<String, List<Drink>> getDrinksByIngredients(List<Ingredient> ingredients);
}
