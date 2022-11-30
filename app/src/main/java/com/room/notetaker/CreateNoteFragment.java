package com.room.notetaker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.room.notetaker.databinding.FragmentCreateNoteBinding;
import com.room.notetaker.mvvm.NotesViewModel;
import com.room.notetaker.mvvm.NotesViewModelFactory;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class CreateNoteFragment extends Fragment {

    FragmentCreateNoteBinding binding;

    @Inject
    NotesViewModelFactory notesViewModelFactory;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateNoteBinding.inflate(inflater,container,false);

        ((NoteApplication) requireActivity().getApplicationContext()).notesComponent.inject_create_note_fragment(this);

        binding.btnSave.setOnClickListener(view -> insertNote());

        binding.ivBackChevron.setOnClickListener(view -> {
            NavDirections action = CreateNoteFragmentDirections.actionCreateNoteFragmentToHomeFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });

        return binding.getRoot();
    }

    private void insertNote() {

        NotesViewModel notesViewModel = new ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel.class);

        String title = binding.etTitle.getText().toString();
        String description = binding.etDescription.getText().toString();
        Date date = Calendar.getInstance().getTime();

        if(title.isEmpty()) {
            binding.etTitle.setError("Title Requires");
            binding.etTitle.requestFocus();
        }else {
            Notes note = new Notes(title,description,date.toString());
            notesViewModel.insertNote(note);
            Toast.makeText(getActivity(), "Note inserted Successfully", Toast.LENGTH_SHORT).show();
            NavDirections action = CreateNoteFragmentDirections.actionCreateNoteFragmentToHomeFragment();
            NavHostFragment.findNavController(this).navigate(action);
        }

    }
}