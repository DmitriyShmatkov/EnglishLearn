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
            parentColumn = "answerId",
            entityColumn = "wordId"
    )
    private Word answer;

    @Relation(
            parentColumn = "questionId",
            entityColumn = "wordId",
            associateBy = @Junction(QuestionWordCrossDef.class)
    )
    private List<Word> options;

    public QuestionWithOptionsAndAnswer(Question question, Word answer, List<Word> options) {
        this.question = question;
        this.answer = answer;
        this.options = options;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Word getAnswer() {
        return answer;
    }

    public void setAnswer(Word answer) {
        this.answer = answer;
    }

    public List<Word> getOptions() {
        return options;
    }

    public void setOptions(List<Word> options) {
        this.options = options;
    }
}
