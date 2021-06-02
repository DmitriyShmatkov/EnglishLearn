package ua.opu.englishlearn.room.repository;

import android.content.Context;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ua.opu.englishlearn.room.database.EnglishLearnDatabase;
import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.dataclasses.FullQuestion;
import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.QuestionWordCrossDef;
import ua.opu.englishlearn.room.entities.Word;

public class EnglishLearnRepository {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private static EnglishLearnRepository instance;
    private EnglishLearnDatabase db;

    public static EnglishLearnRepository getInstance(Context context) {
        if (instance == null) {
            instance = new EnglishLearnRepository(context);
        }
        return instance;
    }

    public EnglishLearnRepository(Context context) {
        db = EnglishLearnDatabase.getInstance(context);
    }

    public void insertWord(Word word) {
        executor.execute(() -> db.wordDAO().insert(word));
    }

    public void insertAllWords(List<Word> words) {
        executor.execute(() -> db.wordDAO().insertAll(words));
    }

    public List<Word> getAllWords() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Word>> result = es.submit(() -> db.wordDAO().getAll());
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }

    public List<Word> getAddedWords() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Word>> result = es.submit(() -> db.wordDAO().getAdded());
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }

    public List<Word> getWordsByPartOfSpeech(String partOfSpeech) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Word>> result = es.submit(() -> db.wordDAO().getByPartOfSpeech(partOfSpeech));
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }

    public List<Word> getMatches(String regex) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Word>> result = es.submit(() -> db.wordDAO().getMatches(regex));
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }

    public void deleteAllWords() {
        executor.execute(() -> db.wordDAO().deleteAll());
    }

    public void updateWord(Word word) {
        executor.execute(() -> db.wordDAO().update(word));
    }


    public long insertQuestion(Question question) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Long> result = es.submit(() -> db.questionDAO().insert(question));
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            es.shutdown();
        }
    }

    public List<FullQuestion> getAllQuestions() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<FullQuestion>> result = es.submit(() -> db.questionDAO().getAll());
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }


    public long insertGame(Game game) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Long> result = es.submit(() -> db.gameDAO().insert(game));
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            es.shutdown();
        }
    }

    public List<FullGame> getAllGames() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<FullGame>> result = es.submit(() -> db.gameDAO().getAll());
        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }


    public void insertQuestionWordCrossDef(QuestionWordCrossDef questionWordCrossDef) {
        executor.execute(() -> db.questionWordCrossDefDAO().insert(questionWordCrossDef));
    }
}
