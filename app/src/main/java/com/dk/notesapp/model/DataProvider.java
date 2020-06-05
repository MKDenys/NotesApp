package com.dk.notesapp.model;

public interface DataProvider {
    void startLoadData();
    void deleteNote(Note note);
    void addNewNote();
    void updateNote(Note note);
    void registerObserver(DataProviderObserver observer);
    void removeObserver(DataProviderObserver observer);
    void notifyObservers();
}
