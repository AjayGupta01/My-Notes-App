package com.project.mynotesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.R
import com.project.mynotesapp.ViewModel.NotesViewModel
import com.project.mynotesapp.databinding.FragmentHomeBinding
import com.project.mynotesapp.ui.adapter.NoteAdapter

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private val notesViewModel:NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        binding.apply {

            setHasOptionsMenu(true)

            notesViewModel.getNotes().observe(viewLifecycleOwner){noteList->
                settingAdapter(noteList)
            }

            allFilterBtn.setOnClickListener {
                notesViewModel.getNotes().observe(viewLifecycleOwner){noteList->
                    settingAdapter(noteList)
                }
            }
            filterHighBtn.setOnClickListener {
                notesViewModel.getHighPriorityNotes().observe(viewLifecycleOwner){noteList->
                    settingAdapter(noteList)
                }
            }
            filterMediumBtn.setOnClickListener {
                notesViewModel.getMediumPriorityNotes().observe(viewLifecycleOwner){noteList->
                    settingAdapter(noteList)
                }
            }
            filterLowBtn.setOnClickListener {
                notesViewModel.getLowPriorityNotes().observe(viewLifecycleOwner){noteList->
                    settingAdapter(noteList)
                }
            }

            createNewNotesBtn.setOnClickListener {
                it.findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment)
            }
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item=menu.findItem(R.id.app_bar_search)
        val searchView=item.actionView as android.widget.SearchView
        searchView.queryHint="Search your note by title..."
        searchView.isSubmitButtonEnabled=false

        searchView.setOnQueryTextListener(object:android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                notesViewModel.searchNotes(query).observe(viewLifecycleOwner){
                    settingAdapter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesViewModel.searchNotes(newText).observe(viewLifecycleOwner){
                    settingAdapter(it)
                }
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun settingAdapter(noteList: List<NotesEntity>) {
       binding.apply {
           notesRecyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
           notesRecyclerView.adapter=NoteAdapter(requireContext(),noteList)
       }
    }

}