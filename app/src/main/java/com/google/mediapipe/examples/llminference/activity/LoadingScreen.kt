package com.google.mediapipe.examples.llminference.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.mediapipe.examples.llminference.InferenceModel
import com.google.mediapipe.examples.llminference.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A route that loads the model and displays a loading indicator while the model is being loaded.
 */
@Composable
internal fun LoadingRoute(
    onModelLoaded: () -> Unit = { }
) {
    val context = LocalContext.current.applicationContext
    var errorMessage by remember { mutableStateOf("") }

    if (errorMessage != "") {
        ErrorMessage(errorMessage)
    } else {
        LoadingIndicator()
    }

    LaunchedEffect(Unit, block = {
        withContext(Dispatchers.IO) {
            try {
                InferenceModel.getInstance(context)
                withContext(Dispatchers.Main) {
                    onModelLoaded()
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "UnknownError"
            }
        }
    })
}

@Composable
fun LoadingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.loading_model),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(
    errorMessage: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}
