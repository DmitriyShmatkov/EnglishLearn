package ua.opu.englishlearn.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int questionId;
    private int correctAnswerId;
    private int userAnswerId;

    public Question(int questionId, int correctAnswerId, int userAnswerId) {
        this.questionId = questionId;
        this.correctAnswerId = correctAnswerId;
        this.userAnswerId = userAnswerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public int getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(int userAnswerId) {
        this.userAnswerId = userAnswerId;
    }
}
