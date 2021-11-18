package com.best.workshop.drinkee.facades.impl;

import com.best.workshop.drinkee.dtos.Drink;
import com.best.workshop.drinkee.dtos.Ingredient;
import com.best.workshop.drinkee.facades.DrinkFacade;
import com.best.workshop.drinkee.services.DrinkService;
import com.best.workshop.drinkee.services.RecipeService;
import com.best.workshop.drinkee.services.RecognitionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinkFacadeImpl implements DrinkFacade {
    private static final String DELIMITER = "&";

    private final DrinkService drinkService;
    private final RecipeService recipeService;
    private final RecognitionService recognitionService;

    public DrinkFacadeImpl(DrinkService drinkService, RecognitionService recognitionService, RecipeService recipeService) {
        this.drinkService = drinkService;
        this.recipeService = recipeService;
        this.recognitionService = recognitionService;
    }

    @Override
    public Map<String, List<Drink>> getDrinksByPictures(List<MultipartFile> pictures) {
        List<Ingredient> ingredients = pictures.parallelStream()
                .map(this.recognitionService::recognizeIngredient)
                .filter(Objects::nonNull)
                .map(ingredient -> URLEncoder.encode(ingredient.toLowerCase(), StandardCharsets.UTF_8))
                .map(Ingredient::new)
                .collect(Collectors.toList());

        Map<String, List<Drink>> ingredientWithDrinks = ingredients.stream()
                .collect(Collectors.toMap(Ingredient::getName, this.drinkService::getDrinksByIngredient));

        Set<Drink> uniqueDrinks = ingredientWithDrinks.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

        Map<String, List<Drink>> result = new TreeMap<>((o1, o2) -> {
            int elementsComparisonResult = Integer.compare(o2.split(DELIMITER).length, o1.split(DELIMITER).length);
            return elementsComparisonResult != 0 ? elementsComparisonResult : o1.compareTo(o2);
        });

        for (Drink drink : uniqueDrinks) {
            List<String> composedKey = new ArrayList<>();

            for (Map.Entry<String, List<Drink>> entry : ingredientWithDrinks.entrySet()) {
                if (entry.getValue().contains(drink)) {
                    composedKey.add(entry.getKey());
                }
            }

            String newKey = String.join(DELIMITER, composedKey);
            if (!result.containsKey(newKey)) {
                result.put(newKey, new ArrayList<>());
            }

            result.get(newKey).add(drink);
        }

        return result;
    }

    @Override
    public Map<String, List<Drink>> getDrinksByIngredients(List<Ingredient> ingredients) {
        return null;
    }
}
