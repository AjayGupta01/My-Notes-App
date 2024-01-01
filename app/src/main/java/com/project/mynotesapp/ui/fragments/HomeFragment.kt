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
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.R
import com.project.mynotesapp.ViewModel.NotesViewModel
import com.project.mynotesapp.databinding.FragmentHomeBinding
import com.project.mynotesapp.ui.adapter.NoteAdapter

class HomeFragment : Fragment(R.layout.fragment_home),MenuProvider,android.widget.SearchView.OnQueryTextListener {
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!
    private val notesViewModel:NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

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

            val menuHost:MenuHost=requireActivity()
            menuHost.addMenuProvider(this@HomeFragment,viewLifecycleOwner,Lifecycle.State.RESUMED)
        }
    }

    private fun searchNote(query: String?){
        val searchQuery="%$query"
        notesViewModel.searchNotes(searchQuery).observe(viewLifecycleOwner){
            settingAdapter(it)
        }
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.search_menu,menu)
        val menuSearch=menu.findItem(R.id.app_bar_search).actionView as android.widget.SearchView
        menuSearch.queryHint="Search note by title..."
        menuSearch.isSubmitButtonEnabled=false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }
    private fun settingAdapter(noteList: List<NotesEntity>) {
       binding.apply {
           notesRecyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
           notesRecyclerView.adapter=NoteAdapter(requireContext(),noteList)
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }
}
