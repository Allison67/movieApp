package eu.tutorials.movies.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import eu.tutorials.movies.MovieViewModel
import eu.tutorials.movies.model.Movie



@Composable
fun MoviesScreen(viewModel: MovieViewModel, navigationToDetail: (Movie) -> Unit) {
    val moviesState by viewModel.moviesState
    var hasFetched by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    Log.d("Debugging", "MoviesScreen Composable")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                    viewModel.fetchMovies()
                } else {
                    Log.d("Debugging", "search")
                    viewModel.searchMovies(query)
                }
            }
        ) {
            Text("Fetch Movies")
        }

        if (hasFetched || moviesState.list.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    moviesState.loading -> {
                        //CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    moviesState.error != null -> {
                        Text(text = "Error OCCURRED: ${moviesState.error}")
                    }

                    else -> {
                        MoviesList(moviesState.list, navigationToDetail)
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
            painter = rememberAsyncImagePainter(movie.poster_path),
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

