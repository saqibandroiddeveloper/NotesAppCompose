package com.osama.notesappcompose.features_notes.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osama.notesappcompose.features_notes.domain.model.InvalidNoteException
import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val useCases: NoteUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf(
        NoteTextFeildState(
            hint = "Title Here... "
        )
    )
    val noteTitle: State<NoteTextFeildState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFeildState(
            hint = "Enter some content"
        )
    )
    val noteContent: State<NoteTextFeildState> = _noteContent

    private val _noteColor = mutableIntStateOf(
        Note.notesColor.random().toArgb()
    )
    val noteColor: State<Int> = _noteColor

    private var currentNoteId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId->
            if (noteId != -1){
                viewModelScope.launch {
                    useCases.getNote(noteId)?.also {note->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false,
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent) {
        when (event) {
            is AddEditEvent.ChangeColor -> {
                _noteColor.value = event.color
            }

            is AddEditEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.content
                )
            }

            is AddEditEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.title
                )
            }

            AddEditEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        useCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = noteColor.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvents.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvents.ShowSnackBar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }


}