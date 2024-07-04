package ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hero.domain.model.SuperheroChat
import com.hero.viewmodels.intents.HeroChatIntents
import com.hero.viewmodels.vms.ChatViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.AppNavigation
import ui.utils.Constants.ASSISTANT
import ui.utils.Constants.USER

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
    chatArguments: AppNavigation.Chat
) {

    val uiStates = viewModel.states.value
    val chatList = listOf(
        SuperheroChat("asdasd", ASSISTANT, "Hi I am the Assistant"),
        SuperheroChat("asdasd", USER, "Hi I am the User"),
        SuperheroChat("asdasd", ASSISTANT, "Hi I am the Assistant")
    )

    var msg by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        chatArguments.apply {
            viewModel.sendIntents(
                HeroChatIntents.SetSuperheroDetails(
                    id = id,
                    name = name,
                    imageUrl = img
                )
            )
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(10.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            items(items = chatList) {
                when (it.role) {
                    ASSISTANT -> {
                        HeroChatResponse(text = it.message)
                    }

                    USER -> {
                        UserChatResponse(text = it.message)
                    }
                }
            }
        }

        OutlinedTextField(value = msg, onValueChange = { msg = it })
    }
}


@Composable
fun HeroChatBubble(modifier: Modifier = Modifier, text: String) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(0.7f).align(Alignment.CenterStart).padding(10.dp)
        )
    }
}

@Composable
fun UserChatBubble(modifier: Modifier = Modifier, text: String) {
    Box(modifier = modifier.fillMaxWidth()) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(0.7f).align(Alignment.CenterEnd).padding(10.dp)
        )
    }
}

