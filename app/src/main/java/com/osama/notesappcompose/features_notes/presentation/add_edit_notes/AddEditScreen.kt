package com.osama.notesappcompose.features_notes.presentation.add_edit_notes

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.osama.notesappcompose.features_notes.domain.model.Note
import com.osama.notesappcompose.features_notes.presentation.add_edit_notes.components.TransparentTextFeild
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel(),
    noteColor: Int
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val noteBackgroundAnimatable = remember {
        Animatable(Color(if (noteColor != -1) noteColor else viewModel.noteColor.value))
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                UiEvents.SaveNote -> {
                  navController.navigateUp()
                }
                is UiEvents.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }



    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditEvent.SaveNote)
            }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState)}
    ) {
         Column(
             modifier = Modifier
                 .fillMaxSize()
                 .background(noteBackgroundAnimatable.value)
                 .padding(16.dp)
         ) {
           Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp),
               horizontalArrangement = Arrangement.SpaceBetween
           ) {
             Note.notesColor.forEach{ color ->
                 val colorInt = color.toArgb()
                 Box(
                     modifier = Modifier
                         .size(50.dp)
                         .shadow(15.dp, CircleShape)
                         .clip(CircleShape)
                         .background(color)
                         .border(
                             width = 3.dp,
                             color = if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                             shape = CircleShape
                         )
                         .clickable {
                             scope.launch {
                                 noteBackgroundAnimatable.animateTo(
                                     targetValue = Color(colorInt),
                                     animationSpec = tween(
                                         durationMillis = 500
                                     )
                                 )
                             }
                             viewModel.onEvent(AddEditEvent.ChangeColor(colorInt))
                         }

                 )
             }
           }
             Spacer(modifier = Modifier.height(16.dp))
             TransparentTextFeild(
                 text = titleState.text,
                 hint = titleState.hint,
                 onValueChange = {
                                 viewModel.onEvent(AddEditEvent.EnteredTitle(it))
                 },
                 onFocusChange = {
                     viewModel.onEvent(AddEditEvent.ChangeTitleFocus(it))
                 },
                 isHintVisible = titleState.isHintVisible,
                 singleLine = true,
                 textStyle = MaterialTheme.typography.titleMedium
             )
             Spacer(modifier = Modifier.height(16.dp))
             TransparentTextFeild(
                 text = contentState.text,
                 hint = contentState.hint,
                 onValueChange = {
                     viewModel.onEvent(AddEditEvent.EnteredContent(it))
                 },
                 onFocusChange = {
                     viewModel.onEvent(AddEditEvent.ChangeContentFocus(it))
                 },
                 isHintVisible = contentState.isHintVisible,
                 textStyle = MaterialTheme.typography.bodyLarge,
                 modifier = Modifier
                     .fillMaxHeight()
             )

         }
    }


}