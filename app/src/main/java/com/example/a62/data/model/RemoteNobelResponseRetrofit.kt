package com.example.a62.data.model

import com.example.a62.domain.model.Laureate
import com.squareup.moshi.Json

data class RemoteNobelResponseRetrofit(
    @Json(name = "nobelPrizes")
    val nobelPrizes: List<RemoteNobelPrize> = emptyList()
)

data class RemoteNobelPrize(
    @Json(name = "awardYear")
    val awardYear: String,
    val category: Localized?
){
    @Json(name = "laureates")
    var laureates: List<RemoteLaureate>? = null
}


data class Localized(
    @Json(name = "en")
    val en: String?
)

data class RemoteLaureate(
    val id: String,
    val knownName: Localized?,
    val fullName: Localized?,
    val motivation: Localized?,
    val birth: RemoteBirth?,
    val links: List<RemoteLink>?
)

data class RemoteBirth(
    val place: RemotePlace?
)

data class RemotePlace(
    val city: String?,
    val country: RemoteCountry?
)

data class RemoteCountry(
    @Json(name = "en")
    val en: String?
)

data class RemoteLink(
    val rel: String?,
    val href: String?
)

fun RemoteNobelPrize.toDomain(): List<Laureate> {
    val list = mutableListOf<Laureate>()
    val cat = this.category?.en ?: ""
    val year = this.awardYear
    this.laureates?.forEach { r ->
        val name = r.fullName?.en ?: r.knownName?.en ?: ""
        val motivation = r.motivation?.en ?: ""
        val birthCountry = r.birth?.place?.country?.en
        val birthPlace = r.birth?.place?.city
        val portrait = r.links?.firstOrNull { it.rel == "image" }?.href
        list.add(
            Laureate(
                id = r.id,
                fullName = name,
                year = year,
                category = cat,
                motivation = motivation,
                birthCountry = birthCountry,
                birthPlace = birthPlace,
                portraitUrl = portrait
            )
        )
    }
    return list
}

