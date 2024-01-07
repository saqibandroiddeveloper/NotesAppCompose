package com.osama.notesappcompose.features_notes.presentation.notes

import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.utill.NoteOrder
import com.osama.notesappcompose.features_notes.domain.utill.OrderType

data class NoteStates(
    val notes:List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible:Boolean  = false
)
