package com.room.notetaker.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.room.notetaker.Notes;

import java.util.List;

public class NotesViewModel extends ViewModel {

    NotesRepository notesRepository;

    public NotesViewModel(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public LiveData<List<Notes>> getNotes() {
        return notesRepository.getNotes();
    }

    public void insertNote(Notes note) {
        notesRepository.insertNote(note);
    }

    public void updateNote(Notes note) {
        notesRepository.updateNote(note);
    }

    public void deleteNote(Notes note) {
        notesRepository.deleteNote(note);
    }


}
