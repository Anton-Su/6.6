package com.example.a66.data.model

import com.example.a66.domain.model.Laureate
import com.example.a66.domain.model.NobelPrize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class RemoteNobelResponseKtor(
    @SerialName("nobelPrizes")
    val nobelPrizes: List<RemoteNobelPrizeKtor> = emptyList()
)

@Serializable
data class RemoteNobelPrizeKtor(
    @SerialName("awardYear")
    val awardYear: String,
    val category: LocalizedKtor?,
    @SerialName("laureates")
    val laureates: List<RemoteLaureateKtor>? = null
)


@Serializable
data class LocalizedKtor(
    @SerialName("en")
    val en: String? = null
)

@Serializable
data class RemoteLaureateKtor(
    val id: String,
    val knownName: LocalizedKtor? = null,
    val portion: String? = null,
    val fullName: LocalizedKtor? = null,
    val motivation: LocalizedKtor? = null,
    val links: List<RemoteLinkKtor> = emptyList()
)


@Serializable
data class RemoteLinkKtor(
    val rel: String? = null,
    val href: String? = null
)

fun RemoteNobelResponseKtor.toDomainList(): List<NobelPrize> = nobelPrizes.map { it.toDomain() }

fun RemoteNobelPrizeKtor.toDomain(): NobelPrize {
    val cat = category?.en ?: ""
    val laureatesList = laureates?.map { r ->
        val name = r.fullName?.en ?: r.knownName?.en ?: ""
        val portion = r.portion ?: "1"
        val motivation = r.motivation?.en ?: ""
        val portrait = r.links.firstOrNull { it.rel == "image" }?.href
        Laureate(
            id = r.id,
            fullName = name,
            portion = portion.toFloatInDelete(),
            motivation = motivation,
            portraitUrl = "https://picsum.photos/${Random.nextInt(100, 301)}"
        )
    } ?: emptyList()
    val id = awardYear + "_" + cat
    return NobelPrize(
        id = id,
        year = awardYear,
        category = cat,
        laureates = laureatesList
    )
}


fun String.toFloatInDelete(): String {
    val z = this.split("/")
    if (z.size == 2) return String.format("%.2f", z[0].toDouble() / z[1].toDouble() * 100) + " %"
    return this + "00 %"
}
