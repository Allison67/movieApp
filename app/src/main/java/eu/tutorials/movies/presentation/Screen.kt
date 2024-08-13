package eu.tutorials.movies.presentation

sealed class Screen(val route: String) {
    object MoviesScreen: Screen("moviesscreen")
    object DetailScreen: Screen("detailscreen")
}