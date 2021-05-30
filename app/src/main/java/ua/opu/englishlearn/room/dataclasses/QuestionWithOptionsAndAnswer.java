package ua.opu.englishlearn.room.dataclasses;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.QuestionWordCrossDef;
import ua.opu.englishlearn.room.entities.Word;

public class QuestionWithOptionsAndAnswer {

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


    public QuestionWithOptionsAndAnswer(Question question, List<Word> options, Word correctAnswer, Word userAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.userAnswer = userAnswer;
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
}
