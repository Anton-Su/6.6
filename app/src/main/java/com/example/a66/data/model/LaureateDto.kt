package com.example.a66.data.model

import com.example.a66.domain.model.Laureate
import kotlinx.serialization.Serializable

@Serializable
data class LaureateDto(
    val id: Int,
    val fullName: String,
    val motivation: String,
    val portion: String? = null,
    val portraitUrl: String? = null
)


fun LaureateDto.toDomain(): Laureate = Laureate(
    id = id,
    fullName = fullName,
    portion = portion?.toFloatInDelete() ?: "100 %",
    motivation = motivation,
    portraitUrl = portraitUrl
)


fun String.toFloatInDelete(): String {
    val z = this.split("/")
    if (z.size == 2) return String.format("%.2f", z[0].toDouble() / z[1].toDouble() * 100) + " %"
    return this + "00 %"
}