package com.dk.notesapp.presentation.start;

interface StartView {
    void showLoadingStage();
    void showErrorMessage(int errorMessage);
    void showNoteList();
}
