package com.project.mynotesapp.Repository

import androidx.lifecycle.LiveData
import com.project.mynotesapp.DAO.NotesDao
import com.project.mynotesapp.Model.NotesEntity

class NotesRepository(val notesDao: NotesDao) {

    fun getNotes() = notesDao.getNotes()

    fun insertNotes(notes:NotesEntity){
        notesDao.insertNote(notes)
    }

    fun updateNotes(notes: NotesEntity){
        notesDao.updateNote(notes)
    }

    fun deleteNotes(id:Int){
        notesDao.deleteNote(id)
    }
    fun getHighPriorityNotes() = notesDao.getHighPriorityNotes()
    fun getMediumPriorityNotes()= notesDao.getMediumPriorityNotes()
    fun getLowPriorityNotes()= notesDao.getLowPriorityNotes()
    fun searchNotes(notesTitle:String?)= notesDao.searchNotes(notesTitle)
}