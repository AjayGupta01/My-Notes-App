package com.project.mynotesapp.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.mynotesapp.Model.NotesEntity

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note:NotesEntity)

    @Update
    fun updateNote(note: NotesEntity)

    @Query("delete from NotesTable where id=:notesId")
    fun deleteNote(notesId:Int)

    @Query("select * from NotesTable order by id desc")
    fun getNotes(): LiveData<List<NotesEntity>>

    @Query("select * from NotesTable where title like :notesTitle")
    fun searchNotes(notesTitle:String?) : LiveData<List<NotesEntity>>

    @Query("select * from NotesTable where priority=1")
    fun getHighPriorityNotes():LiveData<List<NotesEntity>>

    @Query("select * from NotesTable where priority=2")
    fun getMediumPriorityNotes():LiveData<List<NotesEntity>>

    @Query("select * from NotesTable where priority=3")
    fun getLowPriorityNotes():LiveData<List<NotesEntity>>


}