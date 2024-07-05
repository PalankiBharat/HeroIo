package ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hero.domain.model.SuperheroChat
import com.hero.viewmodels.intents.HeroChatIntents
import com.hero.viewmodels.vms.ChatViewModel
import com.kmpalette.loader.rememberNetworkLoader
import com.kmpalette.rememberDominantColorState
import io.ktor.http.Url
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ui.navigation.AppNavigation
import ui.navigation.LocalNavigationProvider
import ui.theme.brighten
import ui.utils.Constants.ASSISTANT
import ui.utils.Constants.USER

@OptIn(KoinExperimentalAPI::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = koinViewModel(),
    chatArguments: AppNavigation.Chat
) {
    val navController = LocalNavigationProvider.current
    val uiStates = viewModel.states.collectAsState().value
    val chatList = uiStates.superheroChats?.reversed() ?: emptyList()

    val networkLoader = rememberNetworkLoader()
    val dominantColorState = rememberDominantColorState(loader = networkLoader)
    LaunchedEffect(chatArguments.img) {
        dominantColorState.updateFrom(Url(chatArguments.img))
    }

    val lazyColumnState = rememberLazyListState()


    var previousChatList by remember { mutableStateOf<List<SuperheroChat>>(emptyList()) }

    // Update the previous chat list whenever the chatList changes
    LaunchedEffect(chatList) {
        delay(500)// For the animation to check the new item
        previousChatList = chatList
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

        ChatHeader(image = chatArguments.img, heroName = chatArguments.name, onBackClick = {
            navController.popBackStack()
        })

        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.95f).weight(1f).background(Color.Black),
            state = lazyColumnState,
            reverseLayout = true,
        ) {
            itemsIndexed(items = chatList, key = { index, item ->
                item.message + index
            }) { index, it ->
                val isNewMessage = !previousChatList.contains(it)
                when (it.role) {
                    ASSISTANT -> {
                        HeroChatResponse(
                            text = it.message,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            isNewMessage = isNewMessage,
                            dominantColor = dominantColorState.color.brighten()
                        )
                    }

                    USER -> {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            UserChatResponse(
                                text = it.message,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                isNewMessage = isNewMessage,
                            )
                        }

                    }
                }
            }
        }

        ChatBox { msg ->
            viewModel.sendIntents(HeroChatIntents.SendChatMessage(message = msg))
        }

    }
}

@Composable
fun ChatBox(modifier: Modifier = Modifier, onSendClick: (msg: String) -> Unit) {
    var msg by remember {
        mutableStateOf("")
    }
    Row(
        modifier = modifier.fillMaxWidth().navigationBarsPadding(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.95f).padding(top = 6.dp),
            value = msg,
            onValueChange = { msg = it },
            trailingIcon = {
                IconButton(onClick = {
                    onSendClick(msg)
                    msg = ""
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


@Composable
fun ChatHeader(
    modifier: Modifier = Modifier,
    image: String,
    heroName: String,
    onBackClick: () -> Unit
) {
    val networkLoader = rememberNetworkLoader()
    val dominantColorState = rememberDominantColorState(loader = networkLoader)
    LaunchedEffect(image) {
        dominantColorState.updateFrom(Url(image))
    }
    Box(modifier = modifier.fillMaxWidth().statusBarsPadding().padding(top = 10.dp)) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center).padding(bottom = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = image,
                contentDescription = "$heroName Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.13f)
                    .aspectRatio(1f)
                    .border(
                        width = 2.dp,
                        color = dominantColorState.color.brighten(),
                        shape = RoundedCornerShape(percent = 100)
                    )
                    .padding(2.dp)
                    .clip(RoundedCornerShape(percent = 100))

            )
            Text(
                text = heroName,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

