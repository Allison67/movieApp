package eu.tutorials.movies.presentation.movie_detail

import eu.tutorials.movies.domain.model.MovieDetail

data class MovieDetailState (
    val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val error: String = ""
)