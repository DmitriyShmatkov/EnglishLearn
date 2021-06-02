package ua.opu.englishlearn.room.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private long questionId;
    private long correctAnswerId;
    private long userAnswerId;
    private long gameId;

    @Ignore
    public Question() {
    }

    public Question(long questionId, long correctAnswerId, long userAnswerId, long gameId) {
        this.questionId = questionId;
        this.correctAnswerId = correctAnswerId;
        this.userAnswerId = userAnswerId;
        this.gameId = gameId;
    }


    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(long correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public long getUserAnswerId() {
        return userAnswerId;
    }

    public void setUserAnswerId(long userAnswerId) {
        this.userAnswerId = userAnswerId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }


    @Override
    @NonNull
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", correctAnswerId=" + correctAnswerId +
                ", userAnswerId=" + userAnswerId +
                ", gameId=" + gameId +
                '}';
    }
}
