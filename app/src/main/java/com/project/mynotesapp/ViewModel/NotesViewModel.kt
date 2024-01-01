package com.project.mynotesapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.mynotesapp.DAO.NotesDao
import com.project.mynotesapp.Database.NotesDatabase
import com.project.mynotesapp.Model.NotesEntity
import com.project.mynotesapp.Repository.NotesRepository

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val notesRepository:NotesRepository

    init {
        val notesDao=NotesDatabase.getInstance(application).getNotesDao()
        notesRepository= NotesRepository(notesDao)
    }

    fun getNotes() = notesRepository.getNotes()

    fun getHighPriorityNotes() = notesRepository.getHighPriorityNotes()

    fun getMediumPriorityNotes() = notesRepository.getMediumPriorityNotes()

    fun getLowPriorityNotes() = notesRepository.getLowPriorityNotes()

    fun insertNotes(notes: NotesEntity) = notesRepository.insertNotes(notes)

    fun updateNotes(notes: NotesEntity) = notesRepository.updateNotes(notes)

    fun deleteNotes(id:Int) = notesRepository.deleteNotes(id)

    fun searchNotes(notesTitle:String?) = notesRepository.searchNotes(notesTitle)

}