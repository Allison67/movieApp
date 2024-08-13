package eu.tutorials.movies.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.tutorials.movies.common.Resource
import eu.tutorials.movies.domain.use_case.GetMoviesUseCase
import eu.tutorials.movies.domain.use_case.GetMoviesWithQueryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: GetMoviesWithQueryUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state

    fun getMovies() {
        getMoviesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieListState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MovieListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchMovies(query: String) {
        searchMoviesUseCase(query).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MovieListState(
                        movies = result.data ?: emptyList()
                    )
                }
                is Resource.Loading -> {
                    _state.value = MovieListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MovieListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}