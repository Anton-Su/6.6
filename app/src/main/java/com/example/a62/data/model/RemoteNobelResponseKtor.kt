package com.example.a62.data.model

import com.example.a62.domain.model.Laureate
import com.example.a62.domain.model.NobelPrize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val birth: RemoteBirthKtor? = null,
    val links: List<RemoteLinkKtor> = emptyList()
)

@Serializable
data class RemoteBirthKtor(
    val place: RemotePlaceKtor? = null
)


@Serializable
data class RemotePlaceKtor(
    val city: String? = null,
    val country: RemoteCountryKtor? = null
)

@Serializable
data class RemoteCountryKtor(
    @SerialName("en")
    val en: String? = null
)

@Serializable
data class RemoteLinkKtor(
    val rel: String? = null,
    val href: String? = null
)

fun RemoteNobelPrizeKtor.toDomainList(): List<NobelPrize> {
    val list = mutableListOf<NobelPrize>()
    val cat = this.category?.en ?: ""
    val year = this.awardYear
    this.laureates?.forEach { r ->
        val name = r.fullName?.en ?: r.knownName?.en ?: ""
        val portion = r.portion ?: "1"
        val motivation = r.motivation?.en ?: ""
        val birthCountry = r.birth?.place?.country?.en
        val birthPlace = r.birth?.place?.city
        val portrait = r.links.firstOrNull { it.rel == "image" }?.href
        list.add(
            NobelPrize(
                id = r.id,
                year = year,
                category = cat,
                laureates = listOf(
                    Laureate(
                        id = r.id,
                        fullName = name,
                        portion = portion,
                        motivation = motivation,
                        birthCountry = birthCountry,
                        birthPlace = birthPlace,
                        portraitUrl = portrait
                    )
                )
            )
        )
    }
    return list
}