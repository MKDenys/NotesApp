package com.dk.notesapp.presentation.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.notesapp.R;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.presentation.MainActivity;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;

import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment implements ListView {
    private ListPresenter presenter;
    private ProgressBar progressBar;
    private TextView textView;
    private RecyclerView recyclerView;
    private NavController navController;
    private MainActivity mainActivity;
    private LinearLayoutManager layoutManager;
    private NotesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        progressBar = view.findViewById(R.id.progressBar_start);
        textView = view.findViewById(R.id.textView_error);
        recyclerView = view.findViewById(R.id.recyclerView_notesList);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        layoutManager = new LinearLayoutManager(requireActivity());
        presenter = new ListPresenter(this, InternetStatusChangeReceiver.getInstance(),
                mainActivity, mainActivity);
        adapter = new NotesAdapter(Collections.<Note>emptyList());
        setupRecyclerView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showLoadingStage() {
        recyclerView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(int errorMessage) {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText(errorMessage);
    }

    @Override
    public void showNoteList(List<Note> notes) {
        textView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setItems(notes);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}