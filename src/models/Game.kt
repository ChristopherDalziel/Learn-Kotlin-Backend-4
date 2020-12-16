package models

import org.bson.codecs.pojo.annotations.BsonId

data class Game(
    @BsonId
    val _id: String? = null,
    val name: String,
    val releaseYear: Int,
    val rating: String,
    val developer: String
)
