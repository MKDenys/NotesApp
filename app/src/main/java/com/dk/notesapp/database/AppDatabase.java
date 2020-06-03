package com.dk.notesapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dk.notesapp.model.Note;

@Database(entities = {Note.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
