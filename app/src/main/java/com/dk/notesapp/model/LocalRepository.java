package com.dk.notesapp.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface LocalRepository {
    LiveData<List<Note>> getAll();
    void insertOrUpdate(List<Note> notes);
    void insertOrUpdate(Note note);
    void delete(Note note);
}
