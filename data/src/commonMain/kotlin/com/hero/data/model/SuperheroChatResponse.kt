import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuperheroChatResponse(
    val id: String?,
    @SerialName("object") val responseType: String?,
    @SerialName("created") val createdTimestamp: Long?,
    @SerialName("model") val modelName: String?,
    val choices: List<Choice>?,
    @SerialName("usage") val usageStatistics: Usage?,
    @SerialName("system_fingerprint") val systemFingerprint: String?,
)

@Serializable
data class Choice(
    @SerialName("index") val choiceIndex: Int?,
    @SerialName("message") val messageDetails: Message?,
    @SerialName("logprobs") val logProbs: String?,
    @SerialName("finish_reason") val finishReason: String?
)

@Serializable
data class Message(
    val role: String?,
    val content: String?
)

@Serializable
data class Usage(
    @SerialName("prompt_tokens") val promptTokens: Int?,
    @SerialName("prompt_time") val promptTime: Double?,
    @SerialName("completion_tokens") val completionTokens: Int?,
    @SerialName("completion_time") val completionTime: Double?,
    @SerialName("total_tokens") val totalTokens: Int?,
    @SerialName("total_time") val totalTime: Double?
)


