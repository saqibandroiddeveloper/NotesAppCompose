package com.osama.notesappcompose.features_notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.osama.notesappcompose.features_notes.domain.model.Note

@Database(
    entities = [Note::class], version = 1
)
abstract class NoteDatabase:RoomDatabase() {
    abstract val noteDao:NoteDao

    companion object{
        val DB_NAME = "notes_db"
    }
}