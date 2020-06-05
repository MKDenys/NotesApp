package com.dk.notesapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dk.notesapp.model.Note;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note ORDER BY lastUpdateDate DESC")
    LiveData<List<Note>> getAll();

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(Note... notes);

    @Delete
    void delete(Note... notes);
}
