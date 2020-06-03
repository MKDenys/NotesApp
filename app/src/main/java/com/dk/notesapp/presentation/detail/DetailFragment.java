package com.dk.notesapp.presentation.detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dk.notesapp.R;
import com.dk.notesapp.model.Note;

public class DetailFragment extends Fragment {
    private EditText title;
    private EditText description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        Note note = (Note) getArguments().getSerializable(getString(R.string.put_serializable_key));
        title = view.findViewById(R.id.editTextText_title);
        description = view.findViewById(R.id.editTextText_description);
        assert note != null;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
    }
}