package eu.tutorials.movies.model

sealed class Screen(val route: String) {
    object MoviesScreen: Screen("moviesscreen")
    object DetailScreen: Screen("detailscreen")
}