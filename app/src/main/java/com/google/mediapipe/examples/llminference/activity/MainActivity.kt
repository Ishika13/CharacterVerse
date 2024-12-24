package com.google.mediapipe.examples.llminference.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.mediapipe.examples.llminference.ChatRoute
import com.google.mediapipe.examples.llminference.R
import com.google.mediapipe.examples.llminference.ui.theme.LLMInferenceTheme

const val START_SCREEN = "start_screen"
const val CHARACTER_SELECTION_SCREEN = "character_selection_screen"
const val CHAT_SCREEN = "chat_screen/{characterName}"

/**
 * Main activity of the app.
 * It sets up the navigation graph and the top app bar.
 * The navigation graph consists of three screens:
 * 1. Start screen: Loading screen that loads the model.
 * 2. Character selection screen: Screen where the user can select a character.
 * 3. Chat screen: Screen where the user can chat with the selected character.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LLMInferenceTheme {
                Scaffold(
                    topBar = { AppBar() }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = START_SCREEN
                        ) {
                            composable(START_SCREEN) {
                                LoadingRoute(
                                    onModelLoaded = {
                                        navController.navigate(CHARACTER_SELECTION_SCREEN) {
                                            popUpTo(START_SCREEN) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            composable(CHARACTER_SELECTION_SCREEN) {
                                CharacterSelectionScreen(navController)
                            }

                            composable(
                                CHAT_SCREEN,
                                arguments = listOf(navArgument("characterName") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val characterName =
                                    backStackEntry.arguments?.getString("characterName")
                                        ?: "Gandalf"
                                ChatRoute(navController, characterName)
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppBar() {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
        }
    }
}