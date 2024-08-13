package eu.tutorials.movies.domain.repository

import eu.tutorials.movies.domain.model.Movie
import eu.tutorials.movies.domain.model.MovieDetail

interface MovieRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMoviesByQuery(query: String): List<Movie>

    suspend fun getMovieById(movieId: String): MovieDetail
}