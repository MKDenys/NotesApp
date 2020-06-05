package com.dk.notesapp.utils.NotesSynchronizer;

public interface NotesSynchronizer {
    void startSync();
    void registerObserver(NotesSynchronizerObserver observer);
    void removeObserver(NotesSynchronizerObserver observer);
    void notifyObservers(SyncStage syncStage);
}
