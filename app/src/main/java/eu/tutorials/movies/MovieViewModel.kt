package eu.tutorials.movies

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.tutorials.movies.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val apiService = ApiService()
    val moviesState = mutableStateOf(MoviesState())

    fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getTrendingMovies()
                Log.d("ViewModel", response.toString())
                moviesState.value = moviesState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                moviesState.value = moviesState.value.copy(
                    loading = false,
                    error = "Error fetching movies ${e.message}"
                )
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.searchMovies(query)
                Log.d("ViewModel", response.toString())
                moviesState.value = moviesState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                moviesState.value = moviesState.value.copy(
                    loading = false,
                    error = "Error fetching movies ${e.message}"
                )
            }
        }
    }

    data class MoviesState(
        val loading: Boolean = true,
        val list: List<Movie> = emptyList(),
        val error: String? = null
    )
}