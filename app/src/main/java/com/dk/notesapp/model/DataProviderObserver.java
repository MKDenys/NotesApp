package com.dk.notesapp.model;

import java.util.List;

public interface DataProviderObserver {
    void onNotesListUpdate(List<Note> notes);
}
