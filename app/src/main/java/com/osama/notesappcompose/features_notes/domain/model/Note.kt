package com.osama.notesappcompose.features_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.osama.notesappcompose.ui.theme.BabyBlue
import com.osama.notesappcompose.ui.theme.LightGreen
import com.osama.notesappcompose.ui.theme.RedOrange
import com.osama.notesappcompose.ui.theme.RedPink
import com.osama.notesappcompose.ui.theme.Violet
@Entity
data class Note(
    val title:String,
    val content:String,
    val timestamp:Long,
    val color:Int,
    @PrimaryKey val id:Int? = null
){
    companion object{
        val notesColor = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
class InvalidNoteException(message:String):Exception(message)
