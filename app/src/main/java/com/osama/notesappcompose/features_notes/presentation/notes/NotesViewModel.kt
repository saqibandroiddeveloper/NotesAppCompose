package com.osama.notesappcompose.features_notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.usecase.NoteUseCases
import com.osama.notesappcompose.features_notes.domain.utill.NoteOrder
import com.osama.notesappcompose.features_notes.domain.utill.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCases: NoteUseCases):ViewModel() {
    private val _state = mutableStateOf(NoteStates())
    val state:State<NoteStates> = _state
   private var recentlyDeletedNote:Note? = null
    private var getNotesJob:Job? = null
    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(notesEvents: NotesEvents){
        when(notesEvents){
            is NotesEvents.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(notesEvents.note)
                    recentlyDeletedNote = notesEvents.note
                }
            }
            is NotesEvents.Order -> {
                if (state.value.noteOrder::class==notesEvents.noteOrder::class &&
                    state.value.noteOrder.orderType==notesEvents.noteOrder.orderType){
                    return
                }else{
                    getNotes(notesEvents.noteOrder)
                }
            }
            NotesEvents.RestoreNote -> {
                viewModelScope.launch {
                    recentlyDeletedNote?.let { useCases.addNote(it) }
                    recentlyDeletedNote = null
                }

            }
            NotesEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
       getNotesJob = useCases.getNotes(noteOrder).onEach {notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}