package com.room.notetaker.di;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.room.notetaker.roomdb.NotesDao;
import com.room.notetaker.roomdb.NotesDatabase;
import com.room.notetaker.utils.Constants;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    public NotesDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, NotesDatabase.class, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    public NotesDao getNoteDao(@NonNull NotesDatabase notesDatabase) {
        return notesDatabase.notesDao();
    }

}
