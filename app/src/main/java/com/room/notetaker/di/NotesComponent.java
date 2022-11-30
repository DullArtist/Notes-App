package com.room.notetaker.di;

import android.content.Context;

import com.room.notetaker.CreateNoteFragment;
import com.room.notetaker.HomeFragment;
import com.room.notetaker.NoteFragment;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {DatabaseModule.class})
public interface NotesComponent {

//    void inject(MainActivity mainActivity);

    void inject_home_fragment(HomeFragment homeFragment);

    void inject_create_note_fragment(CreateNoteFragment createNoteFragment);

    void inject_note_fragment(NoteFragment noteFragment);

    @Component.Factory
    interface Factory {
        NotesComponent Create(@BindsInstance Context context);
    }

}
