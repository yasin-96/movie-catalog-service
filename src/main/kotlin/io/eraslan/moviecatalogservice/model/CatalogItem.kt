package io.eraslan.moviecatalogservice.model

import org.springframework.data.annotation.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

class CatalogItem(
    @Id var id: String,
    var name: String,
    var desc: String,
    var rating : Int,
    var genre: String
) {
}