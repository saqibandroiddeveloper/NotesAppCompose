package com.osama.notesappcompose.features_notes.domain.utill

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}