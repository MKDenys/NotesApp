package com.dk.notesapp.database;

import androidx.lifecycle.LiveData;

import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.App;

import java.util.List;

public class LocalRepository {
    private NoteDao noteDao;

    public LocalRepository() {
        this.noteDao = App.getInstance().getAppDatabase().noteDao();
    }

    public LiveData<List<Note>> getAll() {
        return noteDao.getAll();
    }

    public void insertOrUpdate(List<Note> notes) {
        for (Note note: notes) {
            new InsertOrUpdateAsyncTask(noteDao).execute(note);
        }
    }

    public void delete(Note note){
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
}
