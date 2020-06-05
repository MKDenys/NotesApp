package com.dk.notesapp.presentation.start;

import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.DataProviderObserver;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObservable;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObserver;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizer;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizerObserver;
import com.dk.notesapp.utils.NotesSynchronizer.SyncStage;

import java.util.List;

class StartPresenter implements InternetStatusChangeObserver, DataProviderObserver, NotesSynchronizerObserver {
    private StartView view;
    private InternetStatusChangeObservable internetStatusChangeObservable;
    private DataProvider dataProvider;
    private NotesSynchronizer notesSynchronizer;
    private SyncStage currentSyncStage;

    public StartPresenter(StartView view, InternetStatusChangeObservable internetStatusChangeObservable,
                          DataProvider dataProvider, NotesSynchronizer notesSynchronizer) {
        this.view = view;
        this.internetStatusChangeObservable = internetStatusChangeObservable;
        this.internetStatusChangeObservable.registerObserver(this);
        this.dataProvider = dataProvider;
        this.dataProvider.registerObserver(this);
        this.dataProvider.startLoadData();
        this.notesSynchronizer = notesSynchronizer;
        this.notesSynchronizer.registerObserver(this);
    }

    public void detach() {
        internetStatusChangeObservable.removeObserver(this);
        dataProvider.removeObserver(this);
        notesSynchronizer.removeObserver(this);
        dataProvider = null;
        notesSynchronizer = null;
        view = null;
    }

    @Override
    public void onUpdateSyncStatus(SyncStage syncStage) {
        currentSyncStage = syncStage;
        if (currentSyncStage == SyncStage.COMPLETE_WITH_EMPTY_LIST) {
            view.showErrorMessage(R.string.no_data_error);
        }
    }

    @Override
    public void onUpdateInternetStatus(boolean internetIsAvailable) {
        if (!internetIsAvailable){
            view.showErrorMessage(R.string.no_connection_error);
            currentSyncStage = SyncStage.NOT_STARTED;
        } else if (syncNotStartedOrCompleteWithError()){
            view.showLoadingStage();
            notesSynchronizer.startSync();
        }
    }

    @Override
    public void onNotesListUpdate(List<Note> notes) {
        if (!notes.isEmpty()) {
            view.showNoteList();
            detach();
        }
    }

    private boolean syncNotStartedOrCompleteWithError(){
        return currentSyncStage != SyncStage.STARTED && currentSyncStage != SyncStage.COMPLETE_SUCCESSFULLY;
    }
}
