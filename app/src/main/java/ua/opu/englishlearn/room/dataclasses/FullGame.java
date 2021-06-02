package ua.opu.englishlearn.room.dataclasses;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;

import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.entities.Question;

public class FullGame {

    @Embedded
    private Game game;

    @Relation(
            parentColumn = "gameId",
            entityColumn = "gameId"
    )
    private List<Question> qs;

    @Ignore
    private List<FullQuestion> questions;


    @Ignore
    public FullGame() {
    }

    public FullGame(Game game, List<Question> qs) {
        this.game = game;
        this.qs = qs;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<FullQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<FullQuestion> questions) {
        this.questions = questions;
    }

    public List<Question> getQs() {
        return qs;
    }

    public void setQs(List<Question> qs) {
        this.qs = qs;
    }


    @Override
    @NonNull
    public String toString() {
        return "FullGame{" +
                "game=" + game +
                ", qs=" + qs +
                ", questions=" + questions +
                '}';
    }
}
