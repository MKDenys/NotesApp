package com.dk.notesapp.presentation.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.notesapp.R;
import com.dk.notesapp.model.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Note> notes;

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Note item = notes.get(position);
        itemViewHolder.title.setText(item.getTitle());
        itemViewHolder.description.setText(String.valueOf(item.getDescription()));
        itemViewHolder.datetime.setText(String.valueOf(item.getLastUpdateDate()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setItems(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private TextView datetime;

        private ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            description = itemView.findViewById(R.id.textView_description);
            datetime = itemView.findViewById(R.id.textView_datetime);
        }
    }
}
