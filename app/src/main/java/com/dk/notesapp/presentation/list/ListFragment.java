package com.dk.notesapp.presentation.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.notesapp.MainActivity;
import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.Note;
import com.dk.notesapp.utils.InternetStatusChecker.InternetStatusChangeReceiver;
import com.dk.notesapp.utils.NotesSynchronizer.NotesSynchronizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class ListFragment extends Fragment implements ListView {
    private ListPresenter presenter;
    private ProgressBar progressBarHorizontal;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotesAdapter adapter;

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
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarHorizontal = view.findViewById(R.id.progressBar_sync);
        recyclerView = view.findViewById(R.id.recyclerView_notesList);
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton_add_note);
        fab.setOnClickListener(addNoteBtnClick);
        layoutManager = new LinearLayoutManager(requireActivity());
        setupRecyclerView();
        DataProvider dataProvider = (MainActivity) getActivity();
        NotesSynchronizer notesSynchronizer = (MainActivity) getActivity();
        presenter = new ListPresenter(this, InternetStatusChangeReceiver.getInstance(),
                dataProvider, notesSynchronizer);
    }

    @Override
    public void showNoteList(List<Note> notes) {
        adapter.setItems(notes);
    }

    @Override
    public void showNoteDetail(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.put_serializable_key), note);
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_listFragment_to_detailFragment, bundle);
    }

    @Override
    public void updateList() {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showSyncProcess() {
        showProgressBar();
    }

    @Override
    public void hideSyncProcess() {
        hideProgressBar();
    }

    @Override
    public void showError(int errorStringId) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                errorStringId, Snackbar.LENGTH_LONG).show();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(Collections.<Note>emptyList(), onItemClickListener);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            presenter.deleteNote(adapter.getItem(viewHolder.getAdapterPosition()));
        }
    };

    private View.OnClickListener addNoteBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.addNewNote();
        }
    };

    private NotesAdapter.OnItemClickListener onItemClickListener  = new NotesAdapter.OnItemClickListener() {
        @Override
        public void onItemClickListener(int position) {
            presenter.noteSelected(adapter.getItem(position));
        }
    };

    private void showProgressBar(){
        progressBarHorizontal.setVisibility(View.VISIBLE);
        progressBarHorizontal.setIndeterminate(true);
    }

    private void hideProgressBar(){
        progressBarHorizontal.setVisibility(View.INVISIBLE);
    }
}