package com.osama.notesappcompose.features_notes.presentation.add_edit_notes

sealed class UiEvents {
    data class ShowSnackBar(val message:String):UiEvents()
    data object SaveNote:UiEvents()
}