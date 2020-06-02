package com.dk.notesapp.utils.InternetStatusChecker;

public interface InternetStatusChangeObservable {
    void registerObserver(InternetStatusChangeObserver observer);
    void removeObserver(InternetStatusChangeObserver observer);
    void notifyObservers();
}
