package io.eraslan.moviecatalogservice.controller

import io.eraslan.moviecatalogservice.model.CatalogItem
import io.eraslan.moviecatalogservice.model.Movie
import io.eraslan.moviecatalogservice.model.Rating
import io.eraslan.moviecatalogservice.service.MovieCatalogService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/catalog")
class MovieCatalogController(
    private val movieCatalogService: MovieCatalogService
) {

    @GetMapping("/hello")
    fun hello() : String {
        return "Hello World"
    }

    /*
    //This is an individual endpoint for every user, should maybe get moved
    @GetMapping("/{userId}")
    fun getAllRatedMovies(@PathVariable userId:String) : Flux<CatalogItem>{
        return movieCatalogService.getAllRatedMovies()
    }*/

    @GetMapping("/movie/search")
    fun searchMovie(@RequestParam name:String) : Flux<Movie> {
        return movieCatalogService.searchMovie(name)
    }

    @GetMapping("/test")
    fun testFun(@RequestParam movieIds:List<String>) : Flux<Rating> {
        return movieCatalogService.findRatingByMovieId(movieIds)
    }


/*
    @GetMapping("/movie/search" ,params=["genre"])
    fun findMovieByGenre(@RequestParam genre:String) : Flux<Movie> {
        return movieCatalogService.findMovieByGenre(genre)
    }*/

}