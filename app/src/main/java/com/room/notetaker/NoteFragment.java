package com.room.notetaker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.room.notetaker.databinding.FragmentNoteBinding;
import com.room.notetaker.mvvm.NotesViewModel;
import com.room.notetaker.mvvm.NotesViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private NotesViewModel notesViewModel;

    @Inject
    NotesViewModelFactory notesViewModelFactory;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String title = binding.titleView.getText().toString();
            binding.ivEditNote.setEnabled(!title.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater,container,false);

        ((NoteApplication) requireActivity().getApplicationContext()).notesComponent.inject_note_fragment(this);
        notesViewModel = new ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel.class);

        binding.ivBackChevron.setOnClickListener(view -> {
            NavDirections action = NoteFragmentDirections.actionNoteFragmentToHomeFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });

        Bundle bundle = getArguments();
        Notes note = NoteFragmentArgs.fromBundle(bundle).getNote();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd yyyy, KK mm aaa");
        String date = dateFormat.format(Date.parse(note.getDate()));

        binding.titleView.setText(note.getTitle());
        binding.dateView.setText(date);
        binding.descriptionView.setText(note.getDescription());

        binding.titleView.addTextChangedListener(textWatcher);
        binding.descriptionView.addTextChangedListener(textWatcher);

        binding.ivEditNote.setOnClickListener(view -> {
            String title = binding.titleView.getText().toString().trim();
            String description = binding.descriptionView.getText().toString().trim();

            note.setTitle(title);
            note.setDescription(description);
            note.setDate(Calendar.getInstance().getTime().toString());

            updateNote(note);
        });

        binding.ivDelete.setOnClickListener(view -> deleteNote(note));

        return binding.getRoot();
    }

    private void deleteNote(Notes note) {
        AlertDialog.Builder note_delete_dialog = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        note_delete_dialog.setPositiveButton(R.string.yes, (dialogInterface, i) -> {

            notesViewModel.deleteNote(note);
            NavDirections action = NoteFragmentDirections.actionNoteFragmentToHomeFragment();
            NavHostFragment.findNavController(this).navigate(action);
            Toast.makeText(getActivity(), "Note deleted !", Toast.LENGTH_SHORT).show();

        });
        note_delete_dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel());

        note_delete_dialog.setIcon(R.drawable.ic_baseline_warning_24);
        note_delete_dialog.setTitle("Delete");
        note_delete_dialog.setMessage("Wanna delete this note ?");
        note_delete_dialog.setCancelable(false);

        note_delete_dialog.show();

    }

    private void updateNote(Notes note) {
        notesViewModel.updateNote(note);
        NavDirections action = NoteFragmentDirections.actionNoteFragmentToHomeFragment();
        NavHostFragment.findNavController(this).navigate(action);
        Toast.makeText(getActivity(), "updated", Toast.LENGTH_SHORT).show();
    }

}