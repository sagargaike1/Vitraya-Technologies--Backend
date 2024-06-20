package com.sagar.Service;

import com.sagar.Model.ExtractedText;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class OcrService {
    public List<ExtractedText> extractTextFromImage(String base64Image) {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        ITesseract instance = new Tesseract();
        instance.setLanguage("eng");
        List<ExtractedText> extractedTexts = new ArrayList<>();
        try {
            String result = instance.doOCR(ImageIO.read(bis));
            String[] words = result.split("\\s+");
            for (String word : words) {
                ExtractedText extractedText = new ExtractedText();
                extractedText.setText(word);
                extractedText.setBold(word.equals(word.toUpperCase()));
                extractedTexts.add(extractedText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extractedTexts;
    }
}
