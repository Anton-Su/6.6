package com.example.a62.data.model

import com.example.a62.domain.model.Laureate
import com.example.a62.domain.model.NobelPrize
import com.squareup.moshi.Json
import java.lang.Math.random
import kotlin.random.Random

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
    val portion: String?,
    val fullName: Localized?,
    val motivation: Localized?,
    val links: List<RemoteLink>?
)

data class RemoteLink(
    val rel: String?,
    val href: String?
)

fun RemoteNobelPrize.toDomain(): NobelPrize {
    val cat = this.category?.en ?: ""
    val year = this.awardYear
    val laureatesList = this.laureates?.map { r ->
        val name = r.fullName?.en ?: r.knownName?.en ?: ""
        val portion = r.portion ?: "1"
        val motivation = r.motivation?.en ?: ""
        val portrait = r.links?.firstOrNull { it.rel == "image" }?.href
        Laureate(
            id = r.id,
            fullName = name,
            portion = portion.toFloatInDelete(),
            motivation = motivation,
            portraitUrl = "https://picsum.photos/${Random.nextInt(100, 301)}"
        )
    } ?: emptyList()
    val id = year + "_" + cat
    return NobelPrize(
        id = id,
        year = year,
        category = cat,
        laureates = laureatesList
    )
}
