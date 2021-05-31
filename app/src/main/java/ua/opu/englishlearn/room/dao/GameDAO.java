package ua.opu.englishlearn.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ua.opu.englishlearn.room.dataclasses.FullGame;
import ua.opu.englishlearn.room.entities.Game;

@Dao
public interface GameDAO {

    @Insert
    long insert(Game game);

    @Query("SELECT * FROM Game")
    List<FullGame> getAll();
}
