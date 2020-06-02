package com.dk.notesapp.presentation.start;

import com.dk.notesapp.presentation.BaseView;

interface StartView extends BaseView {
    void showLoadingStage();
    void showErrorMessage(int errorMessage);
}
