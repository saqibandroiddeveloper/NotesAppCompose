package com.osama.notesappcompose.di

import android.app.Application
import androidx.room.Room
import com.osama.notesappcompose.features_notes.data.data_source.NoteDatabase
import com.osama.notesappcompose.features_notes.data.repository.NoteRepositoryImpl
import com.osama.notesappcompose.features_notes.domain.repository.NoteRepository
import com.osama.notesappcompose.features_notes.domain.usecase.AddNote
import com.osama.notesappcompose.features_notes.domain.usecase.DeleteNote
import com.osama.notesappcompose.features_notes.domain.usecase.GetNote
import com.osama.notesappcompose.features_notes.domain.usecase.GetNotes
import com.osama.notesappcompose.features_notes.domain.usecase.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNoteDataBase(app:Application):NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DB_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }
    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}