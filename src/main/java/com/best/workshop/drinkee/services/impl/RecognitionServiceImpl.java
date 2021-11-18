package com.best.workshop.drinkee.services.impl;

import com.best.workshop.drinkee.services.RecognitionService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.IOException;
import java.io.InputStream;


@Service
public class RecognitionServiceImpl implements RecognitionService {
    private final RekognitionClient rekognitionClient;

    public RecognitionServiceImpl() {
        this.rekognitionClient = RekognitionClient.builder()
                .region(Region.EU_CENTRAL_1)
                .build();
    }

    @Override
    public String recognizeIngredient(MultipartFile picture) {
        String result = null;

        try (InputStream inputStream = picture.getInputStream()) {
            SdkBytes sdkBytes = SdkBytes.fromInputStream(inputStream);

            Image awsImage = Image.builder().bytes(sdkBytes).build();

            DetectLabelsRequest request = DetectLabelsRequest.builder().image(awsImage).maxLabels(1).build();
            DetectLabelsResponse response = this.rekognitionClient.detectLabels(request);

            result = response.labels().size() != 0 ? response.labels().get(0).name() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
