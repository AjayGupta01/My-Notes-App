package com.project.mynotesapp.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.R
import com.project.mynotesapp.ViewModel.NotesViewModel
import com.project.mynotesapp.databinding.FragmentCreateNotesBinding
import java.util.Date


class CreateNotesFragment : Fragment(R.layout.fragment_create_notes) {
    private lateinit var binding: FragmentCreateNotesBinding
    private val notesViewModel: NotesViewModel by viewModels()
    var priority= "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCreateNotesBinding.inflate(inflater,container,false)

        binding.apply {

            redPriorityOfCreateNotes.setImageResource(R.drawable.check_icon)
            settingPriority()

            saveNotesBtn.setOnClickListener {
                createNote(it)
            }
        }

        return binding.root
    }



    private fun createNote(it: View?) {
        binding.apply {
            val title=titleOfCreateNotes.text.toString()
            val subtitle=subtitleOfCreateNotes.text.toString()
            val notes=contentOfCreateNotes.text.toString()
            val getDate=Date()
            val date= DateFormat.format("d/MM/yyyy",getDate.time).toString()

            val noteEntity=NotesEntity(null,title,subtitle,notes,date,priority)
            notesViewModel.insertNotes(noteEntity)
            Toast.makeText(requireContext(),"Notes Created Successfully",Toast.LENGTH_SHORT).show()
            it?.findNavController()?.navigate(R.id.action_createNotesFragment_to_homeFragment)
        }
    }

    private fun settingPriority() {
        binding.apply {
            redPriorityOfCreateNotes.setOnClickListener {
                priority= "1"
                redPriorityOfCreateNotes.setImageResource(R.drawable.check_icon)
                yellowPriorityOfCreateNotes.setImageResource(0)
                greenPriorityOfCreateNotes.setImageResource(0)
            }
            yellowPriorityOfCreateNotes.setOnClickListener {
                priority= "2"
                yellowPriorityOfCreateNotes.setImageResource(R.drawable.check_icon)
                redPriorityOfCreateNotes.setImageResource(0)
                greenPriorityOfCreateNotes.setImageResource(0)
            }

            greenPriorityOfCreateNotes.setOnClickListener {
                priority= "3"
                greenPriorityOfCreateNotes.setImageResource(R.drawable.check_icon)
                redPriorityOfCreateNotes.setImageResource(0)
                yellowPriorityOfCreateNotes.setImageResource(0)
            }
        }
    }
}