package com.dk.notesapp.model;

import java.util.List;

public interface DataProviderObserver {
    void onUpdate(List<Note> notes);
}
