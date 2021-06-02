package ua.opu.englishlearn.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import ua.opu.englishlearn.room.entities.Game;
import ua.opu.englishlearn.room.entities.QuestionWordCrossDef;

@Dao
public interface QuestionWordCrossDefDAO {

    @Insert
    void insert(QuestionWordCrossDef questionWordCrossDef);
}
