package com.dk.notesapp.presentation.list;

import com.dk.notesapp.model.Note;
import com.dk.notesapp.presentation.BaseView;

import java.util.List;

interface ListView extends BaseView {
    void showLoadingStage();
    void showErrorMessage(int errorMessage);
    void showNoteList(List<Note> notes);
    void showNoteDetail(Note note);
}
