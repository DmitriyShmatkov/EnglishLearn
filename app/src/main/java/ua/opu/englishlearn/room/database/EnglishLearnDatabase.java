package ua.opu.englishlearn.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import ua.opu.englishlearn.room.converter.Converter;
import ua.opu.englishlearn.room.dao.GameDAO;
import ua.opu.englishlearn.room.dao.QuestionDAO;
import ua.opu.englishlearn.room.dao.QuestionWordCrossDefDAO;
import ua.opu.englishlearn.room.dao.WordDAO;
import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.entities.Question;
import ua.opu.englishlearn.room.entities.QuestionWordCrossDef;
import ua.opu.englishlearn.room.entities.Word;

@Database(entities = {Word.class, Question.class, QuestionWordCrossDef.class, Game.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class EnglishLearnDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "english_learn_db.db";
    private static final Object LOCK = new Object();
    private static volatile EnglishLearnDatabase instance;

    public static EnglishLearnDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null)
                    instance = Room.databaseBuilder(context, EnglishLearnDatabase.class, DATABASE_NAME).build();
            }
        }
        return instance;
    }

    public abstract WordDAO wordDAO();

    public abstract QuestionDAO questionDAO();

    public abstract GameDAO gameDAO();

    public abstract QuestionWordCrossDefDAO questionWordCrossDefDAO();
}
