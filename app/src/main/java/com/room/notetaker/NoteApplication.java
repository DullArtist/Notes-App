package com.room.notetaker;

import android.app.Application;

import com.room.notetaker.di.DaggerNotesComponent;
import com.room.notetaker.di.NotesComponent;

public class NoteApplication extends Application {

    NotesComponent notesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        notesComponent = DaggerNotesComponent.factory().Create(this);
    }
}
