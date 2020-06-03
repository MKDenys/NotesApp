package com.dk.notesapp.network;

import com.dk.notesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class RemoteRepository{

    public List<Note> getAll() {
        String noteTitle = "Note";
        String noteDescription = "Some text, some text, some text";
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
        return notes;
    }
}
