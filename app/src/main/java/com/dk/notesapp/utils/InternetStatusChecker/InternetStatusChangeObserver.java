package com.dk.notesapp.utils.InternetStatusChecker;

public interface InternetStatusChangeObserver {
    void internetStatusWasUpdate(boolean connectionStatus);
}
