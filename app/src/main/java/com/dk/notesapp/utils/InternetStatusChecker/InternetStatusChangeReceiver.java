package com.dk.notesapp.utils.InternetStatusChecker;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dk.notesapp.utils.App;

import java.util.LinkedList;
import java.util.List;


public class InternetStatusChangeReceiver extends BroadcastReceiver implements InternetStatusChangeObservable{
    private static InternetStatusChangeReceiver instance;
    private List<InternetStatusChangeObserver> observers;
    private boolean internetConnectionStatus;

    InternetStatusChangeReceiver(){
        observers = new LinkedList<>();
    }

    public static InternetStatusChangeReceiver getInstance() {
        if (instance == null) {
            instance = new InternetStatusChangeReceiver();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        internetConnectionStatus = App.getInstance().isInternetAvailable();
        notifyObservers();
    }

    @Override
    public void registerObserver(InternetStatusChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(InternetStatusChangeObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (InternetStatusChangeObserver observer : observers)
            observer.internetStatusWasUpdate(internetConnectionStatus);
    }
}
