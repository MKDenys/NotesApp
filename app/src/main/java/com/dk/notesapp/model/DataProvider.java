package com.dk.notesapp.model;

public interface DataProvider {
    void startLoadData();
    void registerObserver(DataProviderObserver observer);
    void removeObserver(DataProviderObserver observer);
    void notifyObservers();
}
