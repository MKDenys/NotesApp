package com.dk.notesapp.database;

import android.os.AsyncTask;

import com.dk.notesapp.model.Note;

public class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
    private NoteDao noteDao;

    public DeleteNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        this.noteDao.delete(notes);
        return null;
    }
}
