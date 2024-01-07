package com.osama.notesappcompose.features_notes.presentation.notes

import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.utill.NoteOrder

sealed class NotesEvents {
    data class Order(val noteOrder: NoteOrder):NotesEvents()
    data class DeleteNote(val note: Note):NotesEvents()
    object RestoreNote:NotesEvents()
    object ToggleOrderSection:NotesEvents()
}