package ua.opu.englishlearn.room.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.opencsv.bean.CsvBindByPosition;

import java.util.Objects;

@Entity
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int wordId;

    @CsvBindByPosition(position = 2)
    private String englishTranslation;

    @CsvBindByPosition(position = 0)
    private String russianTranslation;
    private String partOfSpeech;
    private boolean isAdded;

    @Ignore
    public Word() {

    }

    public Word(int wordId, String englishTranslation, String russianTranslation, String partOfSpeech, boolean isAdded) {
        this.wordId = wordId;
        this.englishTranslation = englishTranslation;
        this.russianTranslation = russianTranslation;
        this.partOfSpeech = partOfSpeech;
        this.isAdded = isAdded;
    }

    @Ignore
    public Word(String englishTranslation, String russianTranslation, String partOfSpeech, boolean isAdded) {
        this.englishTranslation = englishTranslation;
        this.russianTranslation = russianTranslation;
        this.partOfSpeech = partOfSpeech;
        this.isAdded = isAdded;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(String englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public String getRussianTranslation() {
        return russianTranslation;
    }

    public void setRussianTranslation(String russianTranslation) {
        this.russianTranslation = russianTranslation;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(englishTranslation, word.englishTranslation);
    }
}
