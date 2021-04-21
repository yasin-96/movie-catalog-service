package io.eraslan.moviecatalogservice.service

import io.eraslan.moviecatalogservice.model.CatalogItem
import io.eraslan.moviecatalogservice.model.Movie
import io.eraslan.moviecatalogservice.model.Rating
import io.eraslan.moviecatalogservice.repository.MovieCatalogRepository
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux


@Service
class MovieCatalogService(
    private val webClient : WebClient.Builder,
    private val circuitBreakerFactory: ReactiveResilience4JCircuitBreakerFactory
) {

    fun getAllRatings(): Flux<Rating> {
        return webClient.build()
            .get()
            .uri("http://ratings-data-service/ratings/all")
            .retrieve()
            .bodyToFlux(Rating::class.java)
            .transform {
                circuitBreakerFactory.create("catalog-service")
                    .run(
                        it
                    ) {
                        Flux.just(Rating(" ",  0))
                    }
            }
    }
/*
    fun getAllRatedMovies() : Flux<CatalogItem> {
        return getAllRatings()
            .flatMap { rating->
                getMovieInfo(rating.movieId)
                    .map { movie->
                        CatalogItem(movie.id,movie.name," ", rating.rating,//TODO Here we get the genre of the movie from the GenreService)
                    }
            }
            .transform {
                circuitBreakerFactory.create("catalog-service")
                    .run(
                        it
                    ) {
                        Flux.just(Ca(" ", " ", 0))
                    }
            }
    }*/

    fun searchMovie(name:String) : Flux<CatalogItem> {
        return webClient.build()
            .get()
            .uri("http://search-movie-service/search/movies?name=$name")
            .retrieve()
            .bodyToFlux(Movie::class.java)
            .collectList()
            .flatMapMany { movielist->
                val list = ArrayList<String>()
                movielist.map {
                    list.add(it._id)
                }
                Flux.zip(movielist.toFlux(),findRatingByMovieId(list))
                    .map {
                        CatalogItem(it.t1._id,it.t1.name,"",it.t2.rating,"")
                    } //.switchIfEmpty(Flux.just(CatalogItem("","","",10,"")))
            }
    }

    fun findRatingByMovieId(movieIds: List<String>) : Flux<Rating> {
        return webClient.build()
            .get()
            .uri { uribuilder ->
                uribuilder.host("ratings-data-service")
                    .path("/ratings/list")
                    .queryParam("movieIds",movieIds)
                    .build()
            }
            .retrieve()
            .bodyToFlux(Rating::class.java)
    }

    fun findMovieByGenre(genre:String) : Flux<Movie> {
        return webClient.build()
            .get()
            .uri("http://search-movie-service/search/movie?genre=$genre")
            .retrieve()
            .bodyToFlux(Movie::class.java)
    }

    fun getMovieInfo(movieId: String): Mono<Movie> {
        return webClient.build()
            .get()
            .uri("http://movie-info-service/movies/$movieId")
            .retrieve()
            .bodyToMono(Movie::class.java)
            .transform {
                circuitBreakerFactory.create("catalog-service")
                    .run(
                        it
                    ) {
                        Mono.just(Movie(" ", " "))
                    }
            }
    }




}
