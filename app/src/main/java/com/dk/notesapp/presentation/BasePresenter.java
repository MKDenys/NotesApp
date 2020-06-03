package com.dk.notesapp.presentation;

public class BasePresenter {
    protected BaseView view;

    public BasePresenter (BaseView view) {
        attach(view);
    }

    public void attach(BaseView view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
    }
}
