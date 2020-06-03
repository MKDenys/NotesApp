package com.dk.notesapp.database.async;

import android.os.AsyncTask;

import com.dk.notesapp.database.NoteDao;
import com.dk.notesapp.model.Note;

public class InsertOrUpdateAsyncTask extends AsyncTask<Note, Void, Void> {
    private NoteDao noteDao;

    public InsertOrUpdateAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        this.noteDao.insertOrUpdate(notes);
        return null;
    }
}
