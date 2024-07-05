package ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    viewModel: ChatViewModel = koinViewModel(),
    chatArguments: AppNavigation.Chat
) {

    val uiStates = viewModel.states.collectAsState().value
    val chatList = uiStates.superheroChats?.reversed() ?: emptyList()

    val lazyColumnState = rememberLazyListState()

    var msg by remember {
        mutableStateOf("")
    }

    var previousChatList by remember { mutableStateOf(chatList) }
    val newMessageIndex = remember {
        derivedStateOf {
            if (chatList.size > previousChatList.size) chatList.size - 1 else -1
        }
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

    LaunchedEffect(chatList) {
        try {
            lazyColumnState.animateScrollToItem(index = 0)
        } catch (e: Exception) {

        }
        previousChatList = chatList
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).padding(10.dp)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(0.9f).background(Color.Black),
            state = lazyColumnState,
            reverseLayout = true,
        ) {
            items(items = chatList, key = {
                it.message
            }) {
                val isNewMessage = chatList.indexOf(it) == newMessageIndex.value

                when (it.role) {
                    ASSISTANT -> {
                        HeroChatResponse(
                            text = it.message,
                            modifier = Modifier.fillMaxWidth(0.7f),
                            isNewMessage = isNewMessage
                        )
                    }

                    USER -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            UserChatResponse(
                                text = it.message,
                                modifier = Modifier.fillMaxWidth(0.7f)
                            )
                        }

                    }
                }
            }
        }

        Row {
            OutlinedTextField(value = msg, onValueChange = { msg = it }, trailingIcon = {
                IconButton(onClick = {
                    viewModel.sendIntents(HeroChatIntents.SendChatMessage(message = msg))
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Chat Send",
                        tint = Color.White
                    )
                }
            })
        }

    }
}

