package com.dk.notesapp.presentation.detail;

import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.Note;

import java.util.Date;

class DetailPresenter {
    private DataProvider dataProvider;
    private DetailView view;
    private Note note;

    public DetailPresenter(DetailView view, Note note, DataProvider dataProvider) {
        this.view = view;
        this.note = note;
        this.dataProvider = dataProvider;
        this.view.showNoteTitle(note.getTitle());
        this.view.showNoteDescription(note.getDescription());
    }

    public void detach() {
        view = null;
        dataProvider = null;
    }

    public void titleChanged(CharSequence newTitle){
        showSaveButtonIfTextChanged(note.getTitle(), newTitle);
    }

    public void descriptionChanged(CharSequence newDescription){
        showSaveButtonIfTextChanged(note.getDescription(), newDescription);
    }

    public void updateNote(String title, String description){
        note.setTitle(title);
        note.setDescription(description);
        note.setLastUpdateDate(new Date(System.currentTimeMillis()));
        saveNoteUpdates(note);
        view.showNoteList();
    }

    private void showSaveButtonIfTextChanged(String oldText, CharSequence newText){
        if (oldText.contentEquals(newText)){
            view.hideSaveButton();
        } else {
            view.showSaveButton();
        }
    }

    private void saveNoteUpdates(Note note){
        dataProvider.updateNote(note);
    }
}
