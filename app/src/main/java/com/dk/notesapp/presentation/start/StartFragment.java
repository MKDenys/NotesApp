package com.dk.notesapp.presentation.start;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dk.notesapp.MainActivity;
import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizer;

public class StartFragment extends Fragment implements StartView {
    private StartPresenter presenter;
    private ProgressBar progressBarCircle;
    private TextView textViewError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarCircle = view.findViewById(R.id.progressBar_start);
        textViewError = view.findViewById(R.id.textView_error);
        DataProvider dataProvider = (MainActivity) getActivity();
        NotesSynchronizer notesSynchronizer = (MainActivity) getActivity();
        presenter = new StartPresenter(this, InternetStatusChangeReceiver.getInstance(),
                dataProvider, notesSynchronizer);
    }

    @Override
    public void showLoadingStage() {
        textViewError.setVisibility(View.INVISIBLE);
        progressBarCircle.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMessage(int errorMessage) {
        progressBarCircle.setVisibility(View.INVISIBLE);
        textViewError.setVisibility(View.VISIBLE);
        textViewError.setText(errorMessage);
    }

    @Override
    public void showNoteList() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_startFragment_to_listFragment);
    }
}