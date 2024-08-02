package eu.tutorials.movies.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.movies.MovieViewModel
import eu.tutorials.movies.model.Movie
import eu.tutorials.movies.model.Screen

@Composable
fun AppScreen(navController: NavHostController) {
    val movieViewModel: MovieViewModel = viewModel()

    Log.d("Debugging", "AppScreen Composable")

    NavHost(navController = navController, startDestination = Screen.MoviesScreen.route) {
        composable(route = Screen.MoviesScreen.route) {
            Log.d("Debugging", "Navigating to MoviesScreen")
            MoviesScreen(viewModel = movieViewModel, navigationToDetail = {
                navController.currentBackStackEntry?.savedStateHandle?.set("mov", it)
                navController.navigate(Screen.DetailScreen.route)
            })
        }
        composable(route = Screen.DetailScreen.route) {
            val movie = navController.previousBackStackEntry?.savedStateHandle?.
                    get<Movie>("mov") ?: Movie(0, "", "", "")
            MovieDetailScreen(movie = movie)
        }
    }
}

