package com.dk.notesapp.database;

import androidx.lifecycle.LiveData;

import com.dk.notesapp.database.async.DeleteNoteAsyncTask;
import com.dk.notesapp.database.async.InsertOrUpdateAsyncTask;
import com.dk.notesapp.model.LocalRepository;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.App;

import java.util.List;

public class LocalRepositoryImpl implements LocalRepository {
    private NoteDao noteDao;

    public LocalRepositoryImpl() {
        this.noteDao = App.getInstance().getAppDatabase().noteDao();
    }

    @Override
    public LiveData<List<Note>> getAll() {
        return noteDao.getAll();
    }

    @Override
    public void insertOrUpdate(List<Note> notes) {
        for (Note note: notes) {
            insertOrUpdate(note);
        }
    }

    @Override
    public void insertOrUpdate(Note note) {
        new InsertOrUpdateAsyncTask(noteDao).execute(note);
    }

    @Override
    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
}
