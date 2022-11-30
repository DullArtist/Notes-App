package com.room.notetaker.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.room.notetaker.R;
import com.room.notetaker.Notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements Filterable {

    private List<Notes> noteList;
    private final Context context;
    private List<Notes> notesListCopy;
    NoteItemClickListener noteItemClickListener;

    private final int[] colors = {R.color.green_light,R.color.pink_light,R.color.khaki_light,
            R.color.orange_light,R.color.teal_light,R.color.purple_light};

    public interface NoteItemClickListener {
        void OnNoteClick(int position);
    }

    public NoteAdapter(Context context,NoteItemClickListener noteItemClickListener) {
        this.context = context;
        this.noteItemClickListener = noteItemClickListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        Notes notes = noteList.get(position);

        int rand_num = new Random().nextInt(6);
        holder.layout.setBackgroundResource(colors[rand_num]);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        String date = dateFormat.format(Date.parse(notes.getDate()));

        holder.title.setText(notes.getTitle());
        holder.description.setText(date);

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView description;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
            layout = itemView.findViewById(R.id.note_item_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            noteItemClickListener.OnNoteClick(position);
        }
    }

    public void setNoteList(List<Notes> noteList) {
        this.noteList = noteList;
        notesListCopy = new ArrayList<>(noteList);
    }

    @Override
    public Filter getFilter() {
        return filteredNotes;
    }

    private final Filter filteredNotes = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<Notes> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(notesListCopy);
            }else {
                String query = charSequence.toString().toLowerCase().trim();
                for (Notes note : notesListCopy) {
                    if(note.getTitle().toLowerCase().contains(query)) {
                        filteredList.add(note);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            noteList.clear();
            noteList.addAll( (List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
