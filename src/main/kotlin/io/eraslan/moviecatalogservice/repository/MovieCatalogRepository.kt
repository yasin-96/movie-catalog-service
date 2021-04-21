package io.eraslan.moviecatalogservice.repository

import io.eraslan.moviecatalogservice.model.CatalogItem
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieCatalogRepository : ReactiveCrudRepository<CatalogItem,String>