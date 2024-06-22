package com.hero.viewmodels.intents

sealed interface HeroChatIntents {
    data class SendChatMessage(val message:String): HeroChatIntents
    data class SetSuperheroDetails(val id:String, val name:String, val imageUrl:String):HeroChatIntents
}