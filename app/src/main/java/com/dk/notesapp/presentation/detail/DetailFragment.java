package com.dk.notesapp.presentation.detail;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dk.notesapp.MainActivity;
import com.dk.notesapp.R;
import com.dk.notesapp.model.DataProvider;
import com.dk.notesapp.model.Note;

public class DetailFragment extends Fragment implements DetailView{
    private DetailPresenter presenter;
    private EditText title;
    private EditText description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        hideKeyboard();
        presenter.detach();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolBar();
        hideSaveButton();
        title = view.findViewById(R.id.editTextText_title);
        title.addTextChangedListener(titleWatcher);
        description = view.findViewById(R.id.editTextText_description);
        description.addTextChangedListener(descriptionWatcher);
        DataProvider dataProvider = (MainActivity) getActivity();
        presenter = new DetailPresenter(this, getNoteFromArguments(), dataProvider);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            hideKeyboard();
            presenter.updateNote(title.getText().toString(), description.getText().toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNoteTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void showNoteDescription(String description) {
        this.description.setText(description);
    }

    @Override
    public void showSaveButton(){
        setHasOptionsMenu(true);
    }

    @Override
    public void hideSaveButton(){
        setHasOptionsMenu(false);
    }

    @Override
    public void showNoteList() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.popBackStack();
    }

    private void hideKeyboard(){
        InputMethodManager imm =
                (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = requireActivity().getCurrentFocus();
        if (view == null) {
            view = new View(requireActivity());
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private Note getNoteFromArguments(){
        assert getArguments() != null;
        return (Note) getArguments().getSerializable(getString(R.string.put_serializable_key));
    }

    private void setupToolBar(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, appBarConfiguration);
    }

    private TextWatcher titleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (presenter != null) {
                presenter.titleChanged(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher descriptionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (presenter != null) {
                presenter.descriptionChanged(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}