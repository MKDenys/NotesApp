package com.dk.notesapp.utils.InternetStatusChecker;

public interface InternetStatusChangeObserver {
    void onUpdateInternetStatus(boolean internetIsAvailable);
}
