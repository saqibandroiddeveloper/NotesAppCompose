package com.osama.notesappcompose.features_notes.domain.usecase

data class NoteUseCases (
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote
)