package com.dk.notesapp.presentation.list;

import com.dk.notesapp.model.Note;

import java.util.List;

interface ListView {
    void showNoteDetail(Note note);
    void showNoteList(List<Note> notes);
    void updateList();
    void showSyncProcess();
    void hideSyncProcess();
    void showError(int errorStringId);
}
