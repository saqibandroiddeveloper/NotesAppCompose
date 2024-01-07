package com.osama.notesappcompose.features_notes.domain.usecase

import com.osama.notesappcompose.features_notes.domain.model.InvalidNoteException
import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val noteRepository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of note can't be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of note can't be empty")
        }
        noteRepository.insertNote(note)
    }
}