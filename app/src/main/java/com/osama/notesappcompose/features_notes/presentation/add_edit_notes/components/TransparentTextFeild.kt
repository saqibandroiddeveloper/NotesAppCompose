package com.osama.notesappcompose.features_notes.presentation.add_edit_notes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@Composable
fun TransparentTextFeild(
    text:String,
    hint:String,
    modifier: Modifier=Modifier,
    isHintVisible: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onValueChange: (String)->Unit,
    onFocusChange: (FocusState)->Unit
) {

   Box(modifier = modifier){
       BasicTextField(
           value = text,
           onValueChange = onValueChange,
           singleLine = singleLine,
           textStyle = textStyle,
           modifier = Modifier
               .fillMaxWidth()
               .onFocusChanged {
                   onFocusChange(it)
               }
       )
       if (isHintVisible){
           Text(text = hint, style = textStyle, color = Color.DarkGray)
       }
   }

}