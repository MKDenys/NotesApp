package com.dk.notesapp.utils.NewDayDetector;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class NewDayDetector {
    private Timer timer;
    private NewDayDetectorCallback callback;

    public NewDayDetector(NewDayDetectorCallback callback) {
        this.callback = callback;
        timer = new Timer();
        timer.schedule(task, getNextMidnight());
    }

    private Date getNextMidnight(){
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        return tomorrow.getTime();
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            callback.onNewDay();
        }
    };
}
