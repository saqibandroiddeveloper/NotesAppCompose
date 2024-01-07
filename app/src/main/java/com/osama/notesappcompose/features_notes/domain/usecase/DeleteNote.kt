package com.osama.notesappcompose.features_notes.domain.usecase

import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.repository.NoteRepository

class DeleteNote(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        noteRepository.deleteNote(note)
    }
}