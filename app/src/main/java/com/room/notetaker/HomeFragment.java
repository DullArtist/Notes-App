package com.room.notetaker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.room.notetaker.adapters.NoteAdapter;
import com.room.notetaker.databinding.FragmentHomeBinding;
import com.room.notetaker.mvvm.NotesViewModel;
import com.room.notetaker.mvvm.NotesViewModelFactory;

import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment implements NoteAdapter.NoteItemClickListener {

    @Inject
    NotesViewModelFactory notesViewModelFactory;
    private List<Notes> notesList;
    private FragmentHomeBinding binding;
    private NoteAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        ((NoteApplication) requireActivity().getApplicationContext()).notesComponent.inject_home_fragment(this);

        NotesViewModel notesViewModel = new ViewModelProvider(this,notesViewModelFactory).get(NotesViewModel.class);
        notesViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            adapter = new NoteAdapter(getActivity(),this);
            adapter.setNoteList(notes);
            notesList = notes;

            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

            binding.NoteRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            binding.NoteRecyclerView.setAdapter(adapter);
            Log.d("NOTE", "onChanged: " + notes.size());
        });

        binding.fab.setOnClickListener(view -> {

            NavDirections action = HomeFragmentDirections.actionHomeFragmentToCreateNoteFragment();
            NavHostFragment.findNavController(this).navigate(action);
        });

        binding.ivSearch.setOnClickListener(view -> {
            if(binding.searchView.getVisibility() == View.GONE) {
                binding.heading.setVisibility(View.GONE);
                binding.searchView.setVisibility(View.VISIBLE);
                binding.ivSearch.setImageResource(R.drawable.ic_baseline_chevron_left_24);
            }else {
                binding.heading.setVisibility(View.VISIBLE);
                binding.searchView.setVisibility(View.GONE);
                binding.searchView.setQuery("",true);
                binding.ivSearch.setImageResource(R.drawable.search);

            }

        });

       binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               binding.heading.setVisibility(View.VISIBLE);
               binding.searchView.setVisibility(View.GONE);
               binding.ivSearch.setImageResource(R.drawable.search);
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
               return true;
           }
       });


        return binding.getRoot();
    }

    @Override
    public void OnNoteClick(int position) {
        Notes notes = notesList.get(position);

        NavDirections action = HomeFragmentDirections.actionHomeFragmentToNoteFragment(notes);
        NavHostFragment.findNavController(this).navigate(action);

    }
}