package com.dk.notesapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoteRepository{

    public List<Note> getAll() {
        String noteTitle = "Note";
        String noteDescription = "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets. Android is developed by a consortium of developers known as the Open Handset Alliance and commercially sponsored by Google. It was unveiled in 2007, with the first commercial Android device launched in September 2008.";
        List<Note> notes = new ArrayList<>();
        Note note;
        for (int i = 0; i < 5; i++) {
            note = new Note();
            note.setTitle(noteTitle + i);
            note.setDescription(noteDescription);
            note.setId("000" + i);
            notes.add(note);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //return Collections.emptyList();
        return notes;
    }
}
