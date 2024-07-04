package ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = koinViewModel(),
    chatArguments: AppNavigation.Chat.ChatArguments
) {

    val uiStates = viewModel.states.value
    val chatList = uiStates.superheroChats ?: emptyList()

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

    LazyColumn {
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

