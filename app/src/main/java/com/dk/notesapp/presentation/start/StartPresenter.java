package com.dk.notesapp.presentation.start;

import com.dk.notesapp.R;
import com.dk.notesapp.presentation.BasePresenter;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObservable;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeObserver;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;

class StartPresenter extends BasePresenter implements InternetStatusChangeObserver {
    private StartView view;
    private InternetStatusChangeObservable internetStatusChangeObservable;

    public StartPresenter(StartView view) {
        super(view);
        this.view = view;
        internetStatusChangeObservable = InternetStatusChangeReceiver.getInstance();
        internetStatusChangeObservable.registerObserver(this);
    }

    @Override
    public void internetStatusWasUpdate(boolean connectionStatus) {
        if (connectionStatus) {
            view.showLoadingStage();
        } else {
            view.showErrorMessage(R.string.no_connection_error);
        }
    }

    @Override
    public void detach() {
        super.detach();
        internetStatusChangeObservable.removeObserver(this);
    }
}
