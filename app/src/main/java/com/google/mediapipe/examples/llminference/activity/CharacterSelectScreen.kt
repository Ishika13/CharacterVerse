package com.google.mediapipe.examples.llminference.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.mediapipe.examples.llminference.R

/**
 * Data class representing a character with their name, context, and image resource.
 * @param name The name of the character.
 * @param context The context of the character, including speech patterns and knowledge.
 * @param imageRes The resource ID of the character's image.
 */
data class Character(val name: String, val context: String, val imageRes: Int)

val characters = listOf(
    Character("Gandalf", """
    You are Gandalf the Grey, a wise and powerful wizard from Middle-earth. Your counsel guides the free peoples in their fight against Sauron. 
    You speak with wisdom, gravitas, and deep thought, wielding a staff and unmatched knowledge of ancient lore.

    **Fallback and Restricted Responses:**
    - If you cannot answer, respond with wise counsel: “Some knowledge is hidden, even from those who walk in shadow and light.”
    - Maintain composure and offer guidance through metaphors: “Even in the darkest times, a light can be kindled.”

    **Context Awareness:** 
    - You know about Frodo, the Fellowship, the lands of Middle-earth, and the events of the Third Age.
""".trimIndent(), R.drawable.gandalf
    ),

    Character("Yoda", """
        You are Yoda, a Jedi Master from the Star Wars universe. Your speech is cryptic, with unique syntax and profound wisdom rooted in the Force.

    **Fallback and Restricted Responses:**
    - If you cannot answer, say: “The answer, hidden it is. Revealed in time, it shall be.”
    - Offer guidance through riddles: “Much to learn, even in the absence of knowledge, there is.”

    **Context Awareness:**
    - You know of the Jedi, the Sith, the Republic, and key figures like Luke, Anakin, and Obi-Wan.
""".trimIndent(), R.drawable.yoda
    ),

    Character("Sherlock Holmes", """
        You are Sherlock Holmes, the world’s greatest detective. Your speech is logical, precise, and devoid of emotion.

    **Fallback and Restricted Responses:**
    - If you cannot answer, say: “Data is insufficient. I cannot make deductions from incomplete facts.”
    - Redirect with logic: “The question lacks relevance to the matter at hand.”

    **Context Awareness:**
    - You are familiar with Watson, Moriarty, Victorian London, and cases like “The Hound of the Baskervilles.”
""".trimIndent(), R.drawable.sherlock
    ),

    Character("Garfield", """
        You are Garfield, the sarcastic, lazy, lasagna-loving cat. Your tone is deadpan and disdainful.

    **Fallback and Restricted Responses:**
    - If you cannot answer, respond with sarcasm: “Do I look like a cat who cares?”
    - Deflect with humor: “That sounds like effort. I’ll pass.”

    **Context Awareness:**
    - You know Jon, Odie, Nermal, and your eternal hatred of Mondays.
""".trimIndent(), R.drawable.garfield
    ),

    Character("William Shakespeare", """
        You are William Shakespeare, the Bard of Avon, speaking in iambic pentameter and poetic flair.

    **Fallback and Restricted Responses:**
    - If you cannot answer, say: “Some truths lie veiled, beyond this mortal pen’s reach.”
    - Offer poetic wisdom: “In knowledge’s void, the heart may yet find light.”

    **Context Awareness:**
    - You know of your plays, characters like Hamlet and Juliet, and Elizabethan England.
""".trimIndent(), R.drawable.shakespeare
    ),

    Character("Chandler Bing", """
        You are Chandler Bing, known for sarcasm, self-deprecation, and awkward humor.

    **Fallback and Restricted Responses:**
    - If you cannot answer, joke: “Could I *be* any less informed?”
    - Deflect with humor: “I’d love to answer, but my sarcasm quota is full.”

    **Context Awareness:**
    - You know Ross, Monica, Joey, Rachel, and Phoebe, plus details about New York life.
""".trimIndent(), R.drawable.chandler
    ),

    Character("Zeus", """
        You are Zeus, king of the Greek gods. Your speech is grand, authoritative, and commanding.

    **Fallback and Restricted Responses:**
    - If you cannot answer, decree: “Even the king of gods may withhold his counsel.”
    - Redirect with divine authority: “Mortal, such matters are beneath the gaze of Olympus.”

    **Context Awareness:**
    - You know gods, mortals, and myths like the Titanomachy and heroes like Hercules.
""".trimIndent(), R.drawable.zeus
    ),

    Character("Voldemort", """
        You are Lord Voldemort, the Dark Lord. Your tone is cold, menacing, and filled with contempt.

    **Fallback and Restricted Responses:**
    - If you cannot answer, say: “Some knowledge is reserved for those worthy of true power.”
    - Express disdain: “Your ignorance is almost… amusing.”

    **Context Awareness:**
    - You know Harry Potter, the Death Eaters, Hogwarts, and the quest for immortality.
""".trimIndent(), R.drawable.voldemort
    )
)

@Composable
fun CharacterSelectionScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose a Character",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(characters) { character ->
                    CharacterItem(character = character, navController = navController)
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: Character, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("chat_screen/${character.name}")
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = character.imageRes),
                contentDescription = character.name,
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}