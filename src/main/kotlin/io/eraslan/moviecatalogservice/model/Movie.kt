package io.eraslan.moviecatalogservice.model

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


class Movie(
    @Id var _id: String,
    var name: String
)