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
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.R
import com.project.mynotesapp.ViewModel.NotesViewModel
import com.project.mynotesapp.databinding.FragmentEditNotesBinding
import java.text.DateFormat
import java.text.Format
import java.util.Date

class EditNotesFragment : Fragment(R.layout.fragment_edit_notes),MenuProvider {

    val notesData by navArgs<EditNotesFragmentArgs>()
    private var editNoteBinding:FragmentEditNotesBinding? = null
    private val binding get() = editNoteBinding!!
    private val notesViewModel:NotesViewModel by viewModels()
    private lateinit var editNoteView: View
    private var priority="1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        editNoteBinding=FragmentEditNotesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editNoteView=view

        gettingOldValue(notesData)
        settingNewPriority()

        binding.doneBtn.setOnClickListener{
            settingNewValue(editNoteView)
        }

        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }

    private fun settingNewValue(editNoteView:View) {
        binding.apply {
            val title=titleOfEditNotes.text.toString()
            val subtitle=subTitleOfEditNotes.text.toString()
            val notes=contentBoxOfEditNotes.text.toString()
            val id=notesData.NotesData.id
            val getDate=Date()
            val date=android.text.format.DateFormat.format("d/MM/yyyy",getDate.time).toString()

            notesViewModel.updateNotes(NotesEntity(id=id,title=title,subtitle=subtitle,notes=notes,date=date,priority=priority))
            Toast.makeText(requireContext(),"Notes Updated Successfully...",Toast.LENGTH_SHORT).show()
            editNoteView.findNavController().popBackStack(R.id.homeFragment,false)
        }
    }

    private fun settingNewPriority() {
        binding.apply {
            redPriorityOfEditNotes.setOnClickListener{
                priority="1"
                redPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
                yellowPriorityOfEditNotes.setImageResource(0)
                greenPriorityOfEditNotes.setImageResource(0)
            }
            yellowPriorityOfEditNotes.setOnClickListener{
                priority="2"
                yellowPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
                redPriorityOfEditNotes.setImageResource(0)
                greenPriorityOfEditNotes.setImageResource(0)
            }
            greenPriorityOfEditNotes.setOnClickListener{
                priority="3"
                greenPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
                redPriorityOfEditNotes.setImageResource(0)
                yellowPriorityOfEditNotes.setImageResource(0)
            }
        }
    }

    private fun gettingOldValue(oldData:EditNotesFragmentArgs) {
       val oldValue=oldData.NotesData
        binding.apply {
            titleOfEditNotes.setText(oldValue.title)
            subTitleOfEditNotes.setText(oldValue.subtitle)
            contentBoxOfEditNotes.setText(oldValue.notes)
            val oldPriority=oldValue.priority

          when(oldPriority){
              "1"->{
                  priority="1"
                  redPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
              }
              "2"->{
                  priority="2"
                  yellowPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
              }
              "3"->{
                  priority="3"
                  greenPriorityOfEditNotes.setImageResource(R.drawable.check_icon)
              }
          }
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.menuDelete->{
                val bottomSheetDialog=BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
                bottomSheetDialog.setContentView(R.layout.delete_dialog)

                val yesBtn=bottomSheetDialog.findViewById<AppCompatTextView>(R.id.bottomSheetYesBtn)
                val noBtn=bottomSheetDialog.findViewById<AppCompatTextView>(R.id.bottomSheetNoBtn)

                yesBtn?.setOnClickListener {
                    notesViewModel.deleteNotes(notesData.NotesData.id!!)
                    bottomSheetDialog.dismiss()
                    editNoteView.findNavController().popBackStack(R.id.homeFragment,false)
                }
                noBtn?.setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
                bottomSheetDialog.show()
                true
            }
            else->false
        }
    }
}