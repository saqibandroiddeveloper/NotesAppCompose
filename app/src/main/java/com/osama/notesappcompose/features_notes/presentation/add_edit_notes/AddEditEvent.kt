package com.osama.notesappcompose.features_notes.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditEvent {
    data class EnteredTitle(val title : String) : AddEditEvent()
    data class ChangeTitleFocus(val focusState: FocusState ) : AddEditEvent()
    data class EnteredContent(val content : String) : AddEditEvent()
    data class ChangeContentFocus(val focusState: FocusState ) : AddEditEvent()
    data class ChangeColor(val color:Int):AddEditEvent()
    data object SaveNote :AddEditEvent()
}