package com.best.workshop.drinkee.services;

import org.springframework.web.multipart.MultipartFile;

public interface RecognitionService {
    String recognizeIngredient(MultipartFile picture);
}
