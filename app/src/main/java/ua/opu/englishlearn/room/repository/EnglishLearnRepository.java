package ua.opu.englishlearn.room.repository;

import android.content.Context;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ua.opu.englishlearn.room.database.EnglishLearnDatabase;
import ua.opu.englishlearn.room.entities.Word;

public class EnglishLearnRepository {

    private Executor executor = Executors.newSingleThreadExecutor();
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
}