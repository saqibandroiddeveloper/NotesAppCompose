package com.osama.notesappcompose.features_notes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.osama.notesappcompose.features_notes.presentation.add_edit_notes.AddEditScreen
import com.osama.notesappcompose.features_notes.presentation.notes.NoteScreen
import com.osama.notesappcompose.ui.theme.NotesAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.NotesScreen.value
                ) {
                    composable(Routes.NotesScreen.value) {
                        NoteScreen(navController = navController)
                    }
                    composable(
                        Routes.EditNoteScreen.value + "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(
                                name = "noteColor"
                            ){
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ){
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }
}

