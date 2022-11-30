package com.room.notetaker.mvvm;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    NotesRepository notesRepository;

    @Inject
    public NotesViewModelFactory(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(notesRepository);
    }
}
