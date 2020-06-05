package com.dk.notesapp.presentation.list;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.notesapp.R;
import com.dk.notesapp.model.Note;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Note> notes;
    private OnItemClickListener onClickListener;

    public NotesAdapter(List<Note> notes, OnItemClickListener onClickListener) {
        this.notes = notes;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);
        viewHolder = new ItemViewHolder(v, onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final Note item = notes.get(position);
        itemViewHolder.title.setText(item.getTitle());
        itemViewHolder.description.setText(String.valueOf(item.getDescription()));
        Date lastUpdateTime = item.getLastUpdateDate();
        if (isToday(lastUpdateTime)) {
            itemViewHolder.datetime.setText(DateFormat.format("hh:mm", lastUpdateTime));
        } else {
            itemViewHolder.datetime.setText(DateFormat.format("dd.MM.yyyy", lastUpdateTime));
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getItem(int position){
        return this.notes.get(position);
    }

    public void setItems(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    private boolean isToday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR);
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView description;
        private TextView datetime;
        private OnItemClickListener onItemClickListener;

        private ItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.textView_title);
            description = itemView.findViewById(R.id.textView_description);
            datetime = itemView.findViewById(R.id.textView_datetime);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onItemClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }
}
