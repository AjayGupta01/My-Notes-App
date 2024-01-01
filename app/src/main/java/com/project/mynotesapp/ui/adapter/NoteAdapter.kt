package com.project.mynotesapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.R
import com.project.mynotesapp.databinding.EachItemsBinding
import com.project.mynotesapp.ui.fragments.HomeFragment
import com.project.mynotesapp.ui.fragments.HomeFragmentDirections

class NoteAdapter(val context: Context,val noteList:List<NotesEntity>): RecyclerView.Adapter<NotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        return NotesViewHolder(EachItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun getItemCount(): Int {
       return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val data=noteList[position]
        holder.binding.apply {
            titleOfTemplate.text=data.title
            notesOfTemplate.text=data.notes
            dateOfTemplate.text=data.date


            when(data.priority){
                "1"->{
                    priorityOfTemplate.setImageResource(R.drawable.red_dot)
                }
                "2"->{
                    priorityOfTemplate.setImageResource(R.drawable.yellow_dot)
                }
                "3"->{
                    priorityOfTemplate.setImageResource(R.drawable.green_dot)
                }
            }

            root.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
                it.findNavController().navigate(action)
            }



        }
    }
}

class NotesViewHolder(val binding: EachItemsBinding): RecyclerView.ViewHolder(binding.root)