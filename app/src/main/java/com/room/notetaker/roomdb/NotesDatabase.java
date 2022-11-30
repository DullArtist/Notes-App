package com.room.notetaker.roomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.room.notetaker.Notes;

@Database(entities = {Notes.class},version = 1,exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();

}
