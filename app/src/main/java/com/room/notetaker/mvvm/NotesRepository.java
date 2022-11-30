package com.room.notetaker.mvvm;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.room.notetaker.Notes;
import com.room.notetaker.roomdb.NotesDao;

import java.util.List;

import javax.inject.Inject;

public class NotesRepository {

    private final NotesDao notesDao;

    @Inject
    public NotesRepository(NotesDao notesDao) {
        this.notesDao = notesDao;
    }

    public LiveData<List<Notes>> getNotes(){
        return notesDao.getNotes();
    }

    public void insertNote(Notes notes) {
        new insertData(notesDao).execute(notes);
    }

    public void updateNote(Notes notes) {
        new updateData(notesDao).execute(notes);
    }

    public void deleteNote(Notes notes) {
        new deleteData(notesDao).execute(notes);
    }


    private static class insertData extends AsyncTask<Notes,Void,Void> {

        private final NotesDao notesDao;

        public insertData(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class updateData extends AsyncTask<Notes,Void,Void> {

        private final NotesDao notesDao;

        public updateData(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class deleteData extends AsyncTask<Notes,Void,Void> {

        private final NotesDao notesDao;

        public deleteData(NotesDao notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.deleteNote(notes[0]);
            return null;
        }
    }

}
