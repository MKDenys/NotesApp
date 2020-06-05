package com.dk.notesapp.presentation.detail;

interface DetailView {
    void showNoteTitle(String title);
    void showNoteDescription(String description);
    void showSaveButton();
    void hideSaveButton();
    void showNoteList();
}
