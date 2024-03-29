package ua.opu.englishlearn.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ua.opu.englishlearn.room.entities.Word;

@Dao
public interface WordDAO {

    @Insert
    long insert(Word word);

    @Insert
    long[] insertAll(List<Word> words);

    @Query("SELECT * FROM Word")
    List<Word> getAll();

    @Query("SELECT * FROM Word WHERE isAdded = 1 " +
            "ORDER BY englishTranslation ASC")
    List<Word> getAdded();

    @Query("SELECT * FROM Word WHERE partOfSpeech = :partOfSpeech")
    List<Word> getByPartOfSpeech(String partOfSpeech);

    @Query("SELECT * FROM Word WHERE " +
            "englishTranslation LIKE :regex OR russianTranslation LIKE :regex")
    List<Word> getMatches(String regex);

    @Query("DELETE FROM Word")
    void deleteAll();

    @Update
    void update(Word word);
}
