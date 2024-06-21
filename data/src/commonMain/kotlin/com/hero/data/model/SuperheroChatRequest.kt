package com.hero.data.model

import Message
import kotlinx.serialization.SerialName

data class SuperheroChatRequest(
    @SerialName("messages")
    val messageList: List<Message?>?,
    @SerialName("model")
    val modelName: String?,
)