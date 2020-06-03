package com.dk.notesapp.presentation.list;

import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.DataProviderObserver;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.presentation.BasePresenter;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObservable;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObserver;
import com.dk.notesapp.utils.NotesSynchronizer;

import java.util.List;

class ListPresenter extends BasePresenter implements InternetStatusChangeObserver, DataProviderObserver {
    private ListView view;
    private InternetStatusChangeObservable internetStatusChangeObservable;
    private DataProvider dataProvider;
    private NotesSynchronizer notesSynchronizer;
    private List<Note> notes;

    public ListPresenter(ListView view, InternetStatusChangeObservable internetStatusChangeObservable,
                         DataProvider dataProvider, NotesSynchronizer notesSynchronizer) {
        super(view);
        this.view = view;
        this.internetStatusChangeObservable = internetStatusChangeObservable;
        this.internetStatusChangeObservable.registerObserver(this);
        this.dataProvider = dataProvider;
        this.dataProvider.registerObserver(this);
        this.notesSynchronizer = notesSynchronizer;
        this.dataProvider.startLoadData();
    }

    @Override
    public void internetStatusWasUpdate(boolean connectionStatus) {
        if (connectionStatus) {
            view.showLoadingStage();
            //notesSynchronizer.startSync();
        } else {
            view.showErrorMessage(R.string.no_connection_error);
        }
    }

    @Override
    public void detach() {
        super.detach();
        internetStatusChangeObservable.removeObserver(this);
        dataProvider.removeObserver(this);
    }

    @Override
    public void onUpdate(List<Note> notes) {
        this.notes = notes;
        if (notes.isEmpty()) {
            view.showErrorMessage(R.string.no_data_error);
        } else {
            view.showNoteList(notes);
        }
    }
}
