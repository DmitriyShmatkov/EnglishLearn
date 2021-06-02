package ua.opu.englishlearn.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.entities.Game;

@Dao
public interface GameDAO {

    @Insert
    long insert(Game game);

    @Transaction
    @Query("SELECT * FROM Game " +
            "ORDER BY date DESC")
    List<FullGame> getAll();
}
