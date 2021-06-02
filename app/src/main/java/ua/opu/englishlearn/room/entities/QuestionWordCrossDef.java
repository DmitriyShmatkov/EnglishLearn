package ua.opu.englishlearn.room.entities;

import androidx.room.Entity;

@Entity(primaryKeys = {"questionId", "wordId"})
public class QuestionWordCrossDef {

    private long questionId;
    private long wordId;

    public QuestionWordCrossDef(long questionId, long wordId) {
        this.questionId = questionId;
        this.wordId = wordId;
    }


    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
}
