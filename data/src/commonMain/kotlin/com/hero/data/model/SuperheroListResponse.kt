package data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@kotlinx.serialization.Serializable
data class PowerStats(
	val strength: Int? = null,
	val durability: Int? = null,
	val combat: Int? = null,
	val power: Int? = null,
	val speed: Int? = null,
	val intelligence: Int? = null
)

@kotlinx.serialization.Serializable
data class Appearance(
	val gender: String? = null,
	val race: String? = null,
	val eyeColor: String? = null,
	val weight: List<String?>? = null,
	val hairColor: String? = null,
	val height: List<String?>? = null
)

@kotlinx.serialization.Serializable
data class Superhero(
	val images: Images? = null,
	val appearance: Appearance? = null,
	val work: Work? = null,
	val name: String? = null,
	val powerstats: PowerStats? = null,
	val id: Int? = null,
	val biography: Biography? = null,
	val slug: String? = null,
	val connections: Connections? = null
)

@kotlinx.serialization.Serializable
data class Connections(
	val groupAffiliation: String? = null,
	val relatives: String? = null
)

@kotlinx.serialization.Serializable
data class Work(
	val occupation: String? = null,
	val base: String? = null
)

@kotlinx.serialization.Serializable
data class Images(
	val md: String? = null,
	val sm: String? = null,
	val xs: String? = null,
	val lg: String? = null
)

@kotlinx.serialization.Serializable
data class Biography(
	val firstAppearance: String? = null,
	val placeOfBirth: String? = null,
	val aliases: List<String?>? = null,
	val fullName: String? = null,
	val publisher: String? = null,
	val alterEgos: String? = null,
	val alignment: String? = null
)

