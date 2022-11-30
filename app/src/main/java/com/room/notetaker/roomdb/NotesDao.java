package com.room.notetaker.roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.room.notetaker.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * from Notes")
    LiveData<List<Notes>> getNotes();

    @Insert
    void insertNote(Notes note);

    @Update
    void updateNote(Notes note);

    @Delete
    void deleteNote(Notes notes);

    @Query("SELECT * from Notes WHERE title LIKE :query OR description LIKE :query")
    LiveData<List<Notes>> getFilteredNotes(String query);



}
