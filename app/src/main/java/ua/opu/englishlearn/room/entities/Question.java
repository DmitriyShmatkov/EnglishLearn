package ua.opu.englishlearn.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int questionId;
    private int answerId;

    public Question(int questionId, int answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
