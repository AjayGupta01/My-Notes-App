package com.project.mynotesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mynotesapp.DAO.NotesDao
import com.project.mynotesapp.Model.NotesEntity

@Database(entities = [NotesEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase(){

    abstract fun getNotesDao():NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase ? = null

        fun getInstance(context: Context) : NotesDatabase{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "NotesDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }

    }
}