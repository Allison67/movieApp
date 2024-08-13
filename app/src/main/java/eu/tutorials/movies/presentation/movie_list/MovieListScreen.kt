package eu.tutorials.movies.presentation.movie_list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.movies.common.Constants
import eu.tutorials.movies.domain.model.Movie
import eu.tutorials.movies.presentation.Screen



@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var query by remember { mutableStateOf("") }
    var hasFetched by remember { mutableStateOf(false) }


    Log.d("Debugging", "MoviesScreen Composable")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (hasFetched) Arrangement.Top else Arrangement.Center
    ) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Movies") },
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = {
                Log.d("Debugging", "button clicked")
                hasFetched = true
                if (query.isEmpty()) {
                    Log.d("Debugging", "fetch")
                    viewModel.getMovies()
                } else {
                    Log.d("Debugging", "search")
                    viewModel.searchMovies(query)
                }
            }
        ) {
            Text("Fetch Movies")
        }

        AnimatedVisibility(
            visible = hasFetched || state.isLoading || state.movies.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    state.error.isNotBlank() -> {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )
                    }

                    state.movies.isNotEmpty() -> {
                        MoviesList(movies = state.movies, navigationToDetail = {
                            navController.navigate(Screen.DetailScreen.route + "/${it.id}")
                        })
                    }
                }
            }
        }

    }

}

@Composable
fun MoviesList(movies: List<Movie>, navigationToDetail: (Movie) -> Unit){
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(movies) {
                movie ->
            MovieItem(movie = movie, navigationToDetail)
        }
    }
}


@Composable
fun MovieItem(movie: Movie, navigationToDetail: (Movie) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigationToDetail(movie) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            // get the image async
            painter = rememberAsyncImagePainter(Constants.POSTER_BASE_URL + movie.posterPath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f) // as weight as its height
        )
        Text(
            text = movie.title,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top=4.dp)
        )
    }
}

