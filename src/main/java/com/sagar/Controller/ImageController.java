package com.sagar.Controller;

import com.sagar.Model.ExtractedText;
import com.sagar.Model.Image;
import com.sagar.Repository.ImageRepository;
import com.sagar.Service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/public/api/images")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private OcrService ocrService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            List<ExtractedText> extractedTexts = ocrService.extractTextFromImage(base64Image);

            Image image = new Image();
            image.setBase64Image(base64Image);
            image.setExtractedTexts(extractedTexts);

            imageRepository.save(image);
            return ResponseEntity.ok("Image uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageDetails(@PathVariable Long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (!imageOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
        }
        return ResponseEntity.ok(imageOpt.get());
    }
}
