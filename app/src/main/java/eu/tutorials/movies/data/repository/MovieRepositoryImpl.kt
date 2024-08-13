package eu.tutorials.movies.data.repository

import eu.tutorials.movies.data.remote.ApiService
import eu.tutorials.movies.data.remote.dto.toMovie
import eu.tutorials.movies.data.remote.dto.toMovieDetail
import eu.tutorials.movies.domain.model.Movie
import eu.tutorials.movies.domain.model.MovieDetail
import eu.tutorials.movies.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return api.getTrendingMovies().results.map { it.toMovie() }
    }

    override suspend fun getMoviesByQuery(query: String): List<Movie> {
        return api.searchMovies(query).results.map { it.toMovie() }
    }

    override suspend fun getMovieById(movieId: String): MovieDetail {
        return api.getMovieDetails(movieId).toMovieDetail()
    }

}