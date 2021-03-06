package com.dk.notesapp.presentation.list;

import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.DataProviderObserver;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObservable;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObserver;
import com.dk.notesapp.utils.NewDayDetector.NewDayDetector;
import com.dk.notesapp.utils.NewDayDetector.NewDayDetectorCallback;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizer;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizerObserver;
import com.dk.notesapp.utils.NotesSynchronizer.SyncStage;

import java.util.List;

class ListPresenter implements InternetStatusChangeObserver, DataProviderObserver,
        NotesSynchronizerObserver, NewDayDetectorCallback {
    private ListView view;
    private InternetStatusChangeObservable internetStatusChangeObservable;
    private DataProvider dataProvider;
    private NotesSynchronizer notesSynchronizer;
    private SyncStage currentSyncStage;

    public ListPresenter(ListView view, InternetStatusChangeObservable internetStatusChangeObservable,
                         DataProvider dataProvider, NotesSynchronizer notesSynchronizer) {
        this.view = view;
        this.internetStatusChangeObservable = internetStatusChangeObservable;
        this.internetStatusChangeObservable.registerObserver(this);
        this.dataProvider = dataProvider;
        this.dataProvider.registerObserver(this);
        this.notesSynchronizer = notesSynchronizer;
        this.notesSynchronizer.registerObserver(this);
        new NewDayDetector(this);
    }

    public void detach() {
        internetStatusChangeObservable.removeObserver(this);
        dataProvider.removeObserver(this);
        notesSynchronizer.removeObserver(this);
        view = null;
        dataProvider = null;
        notesSynchronizer = null;
    }

    @Override
    public void onUpdateSyncStatus(SyncStage syncStage) {
        setCurrentSyncStage(syncStage);
    }

    @Override
    public void onUpdateInternetStatus(boolean internetIsAvailable) {
        if (syncNotStartedOrCompleteWithError()){
            if (internetIsAvailable){
                notesSynchronizer.startSync();
            }
        } else if (currentSyncStage == SyncStage.STARTED) {
            if (!internetIsAvailable){
                view.showError(R.string.no_connection_error);
                setCurrentSyncStage(SyncStage.NOT_STARTED);
            }
        }
    }

    @Override
    public void onNotesListUpdate(List<Note> notes) {
        view.showNoteList(notes);
    }

    @Override
    public void onNewDay() {
        view.updateList();
    }

    public void deleteNote(Note note){
        dataProvider.deleteNote(note);
    }

    public void addNewNote(){
        dataProvider.addNewNote();
    }

    public void noteSelected(Note note){
        view.showNoteDetail(note);
    }

    private void setCurrentSyncStage(SyncStage currentSyncStage) {
        this.currentSyncStage = currentSyncStage;
        if (currentSyncStage == SyncStage.STARTED){
            view.showSyncProcess();
        } else {
            view.hideSyncProcess();
        }
    }

    private boolean syncNotStartedOrCompleteWithError(){
        return currentSyncStage != SyncStage.STARTED && currentSyncStage != SyncStage.COMPLETE_SUCCESSFULLY;
    }
}
