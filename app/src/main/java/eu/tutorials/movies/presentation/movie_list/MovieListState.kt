package eu.tutorials.movies.presentation.movie_list

import eu.tutorials.movies.domain.model.Movie

data class MovieListState (
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = ""
)