package com.osama.notesappcompose.features_notes.domain.usecase

import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id:Int): Note?{
        return repository.getNoteById(id)
    }
}