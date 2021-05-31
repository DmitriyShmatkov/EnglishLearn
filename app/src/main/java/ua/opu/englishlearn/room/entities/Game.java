package ua.opu.englishlearn.room.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Game {

    @PrimaryKey
    private long gameId;
    private int questionsNumber;
    private int correctAnswers;
    private Date date;

    public Game(long gameId, int questionsNumber, int correctAnswers, Date date) {
        this.gameId = gameId;
        this.questionsNumber = questionsNumber;
        this.correctAnswers = correctAnswers;
        this.date = date;
    }

    @Ignore
    public Game(int questionsNumber, int correctAnswers, Date date) {
        this.questionsNumber = questionsNumber;
        this.correctAnswers = correctAnswers;
        this.date = date;
    }

    @Ignore
    public Game(int questionsNumber, int correctAnswers) {
        this.questionsNumber = questionsNumber;
        this.correctAnswers = correctAnswers;
    }

    @Ignore
    public Game() {

    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getQuestionsNumber() {
        return questionsNumber;
    }

    public void setQuestionsNumber(int questionsNumber) {
        this.questionsNumber = questionsNumber;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
