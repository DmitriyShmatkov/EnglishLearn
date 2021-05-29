package ua.opu.englishlearn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {

    private String englishTranslation;
    private String russianTranslation;
}
