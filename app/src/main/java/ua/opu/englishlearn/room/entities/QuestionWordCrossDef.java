package ua.opu.englishlearn.room.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"questionId", "wordId"})
public class QuestionWordCrossDef {

    private int questionId;
    private int wordId;

    public QuestionWordCrossDef(int questionId, int wordId) {
        this.questionId = questionId;
        this.wordId = wordId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
}
