package com.example.a62.data.model

import com.example.a62.domain.model.Laureate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteNobelResponseKtor(
    //@Json(name = "nobelPrizes")
    @SerialName("nobelPrizes")
    val nobelPrizes: List<RemoteNobelPrize> = emptyList()
)
//
////@Serializable
////data class RemoteNobelPrize(
////    //@Json(name = "awardYear")
////    @SerialName("awardYear")
////    val awardYear: String,
////    val category: Localized?
////){
////    // laureates may be absent for some entries
////    // @Json(name = "laureates")
////    @SerialName("laureates")
////    var laureates: List<RemoteLaureate>? = null
////}
//
//@Serializable
//data class RemoteNobelPrize(
//    @SerialName("awardYear")
//    val awardYear: String,
//
//    val category: Localized?,
//
//    @SerialName("laureates")
//    val laureates: List<RemoteLaureate>? = null
//)
//
//
//@Serializable
//data class Localized(
//    //@Json(name = "en")
//    @SerialName("en")
//    val en: String?
//)
//
//@Serializable
//data class RemoteLaureate(
//    val id: String,
//    val knownName: Localized?,
//    val fullName: Localized?,
//    val motivation: Localized?,
//    val birth: RemoteBirth?,
//    val links: List<RemoteLink>?
//)
//
//@Serializable
//data class RemoteBirth(
//    val place: RemotePlace?
//)
//
//
//@Serializable
//data class RemotePlace(
//    val city: String?,
//    val country: RemoteCountry?
//)
//
//@Serializable
//data class RemoteCountry(
//    //@Json(name = "en")
//    @SerialName("en")
//    val en: String?
//)
//
//@Serializable
//data class RemoteLink(
//    val rel: String?,
//    val href: String?
//)
//
//fun RemoteNobelPrize.toDomainList(): List<Laureate> {
//    val list = mutableListOf<Laureate>()
//    val cat = this.category?.en ?: ""
//    val year = this.awardYear
//    this.laureates?.forEach { r ->
//        val name = r.fullName?.en ?: r.knownName?.en ?: ""
//        val motivation = r.motivation?.en ?: ""
//        val birthCountry = r.birth?.place?.country?.en
//        val birthPlace = r.birth?.place?.city
//        val portrait = r.links?.firstOrNull { it.rel == "image" }?.href
//        list.add(
//            Laureate(
//                id = r.id,
//                fullName = name,
//                year = year,
//                category = cat,
//                motivation = motivation,
//                birthCountry = birthCountry,
//                birthPlace = birthPlace,
//                portraitUrl = portrait
//            )
//        )
//    }
//    return list
//}