package ua.opu.englishlearn.room.dataclasses;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.QuestionWordCrossDef;
import ua.opu.englishlearn.room.entities.Word;

public class FullQuestion {

    @Embedded
    private Question question;

    @Relation(
            parentColumn = "questionId",
            entityColumn = "wordId",
            associateBy = @Junction(QuestionWordCrossDef.class)
    )
    private List<Word> options;

    @Relation(
            parentColumn = "correctAnswerId",
            entityColumn = "wordId"
    )
    private Word correctAnswer;

    @Relation(
            parentColumn = "userAnswerId",
            entityColumn = "wordId"
    )
    private Word userAnswer;


    public FullQuestion(Question question, List<Word> options, Word correctAnswer, Word userAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
    }

    @Ignore
    public FullQuestion() {
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Word> getOptions() {
        return options;
    }

    public void setOptions(List<Word> options) {
        this.options = options;
    }

    public Word getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Word correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Word getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Word userAnswer) {
        this.userAnswer = userAnswer;
    }


    @Override
    public String toString() {
        return "FullQuestion{" +
                "question=" + question +
                ", options=" + options +
                ", correctAnswer=" + correctAnswer +
                ", userAnswer=" + userAnswer +
                '}';
    }
}
