import com.hero.data.model.SuperheroChatEntity

fun Choice.toChatEntity(superheroId:String) = SuperheroChatEntity(
    superheroID = superheroId,
    role = messageDetails?.role?:"system",
    message = messageDetails?.content?:""
)