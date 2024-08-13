package eu.tutorials.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.movies.presentation.movie_detail.MovieDetailScreen
import eu.tutorials.movies.presentation.movie_list.MovieListScreen
import eu.tutorials.movies.presentation.ui.theme.MoviesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.MoviesScreen.route) {
                        composable(
                            route = Screen.MoviesScreen.route
                        ) {
                            MovieListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.DetailScreen.route + "/{movieId}"
                        ) {
                            MovieDetailScreen()
                        }
                    }
                }
            }
        }
    }
}

