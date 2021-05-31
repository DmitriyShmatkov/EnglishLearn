package ua.opu.englishlearn.room.dao;

import android.media.MediaCodec;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ua.opu.englishlearn.room.dataclasses.FullQuestion;
import ua.opu.englishlearn.room.entities.Question;

@Dao
public interface QuestionDAO {

    @Insert
    long insert(Question question);

    @Query("SELECT * FROM Question")
    List<FullQuestion> getAll();
}
