package io.eraslan.moviecatalogservice.model

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("rating")
class Rating(
    @Id var movieId: String,
    var rating: Int
) {
}