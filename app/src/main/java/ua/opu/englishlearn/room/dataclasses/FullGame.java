package ua.opu.englishlearn.room.dataclasses;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.util.List;

import ua.opu.englishlearn.room.entities.Game;

public class FullGame {

    @Embedded
    private Game game;

    @Relation(
            parentColumn = "gameId",
            entityColumn = "gameId"
    )
    private List<FullQuestion> questions;


    public FullGame(Game game, List<FullQuestion> questions) {
        this.game = game;
        this.questions = questions;
    }

    @Ignore
    public FullGame() {

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
}
