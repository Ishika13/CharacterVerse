package com.google.mediapipe.examples.llminference

import android.content.Context
import android.util.Log
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import java.io.File
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Wrapper class for the LLM inference model.
 * @param context The context of the application.
 * @throws IllegalArgumentException If the model file is not found at the specified path.
 */
class InferenceModel private constructor(context: Context) {
    private var llmInference: LlmInference

    private val modelExists: Boolean
        get() = File(MODEL_PATH).exists()

    private val _partialResults = MutableSharedFlow<Pair<String, Boolean>>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val partialResults: SharedFlow<Pair<String, Boolean>> = _partialResults.asSharedFlow()

    init {
        if (!modelExists) {
            throw IllegalArgumentException("Model not found at path: $MODEL_PATH")
        }

        val options = LlmInference.LlmInferenceOptions.builder()
            .setModelPath(MODEL_PATH)
            .setMaxTokens(1024)
            .setResultListener { partialResult, done ->
                val cleanedResult = processModelResponse(partialResult)
                _partialResults.tryEmit(cleanedResult to done)
            }
            .build()

        llmInference = LlmInference.createFromOptions(context, options)
    }

    fun generateResponseAsync(prompt: String) {
        val cleanPrompt = prompt.replace("Please tell me", "")
            .replace("Can you", "")
            .replace("Could you", "")
            .replace("Sure, here is the response", "")
            .replace("Sure, here's the response", "")
            .trim()

        val gemmaPrompt = cleanPrompt + "<start_of_turn>model\n"
        Log.d("InferenceModel", "Generating response for prompt: $gemmaPrompt")
        llmInference.generateResponseAsync(gemmaPrompt)
    }

    private fun processModelResponse(response: String): String {
        return response
    }

    companion object {
        private const val MODEL_PATH = "/data/local/tmp/llm/gemma-2b-it-cpu-int4.bin"
        private var instance: InferenceModel? = null

        fun getInstance(context: Context): InferenceModel {
            return if (instance != null) {
                instance!!
            } else {
                InferenceModel(context).also { instance = it }
            }
        }
    }
}