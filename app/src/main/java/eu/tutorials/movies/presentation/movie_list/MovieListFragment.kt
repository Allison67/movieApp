package eu.tutorials.movies.presentation.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.movies.R
import eu.tutorials.movies.databinding.FragmentMovieListBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        val movieAdapter = MovieAdapter { movie ->
            navController.navigate(
                R.id.action_movieListFragment_to_movieDetailFragment,
                Bundle().apply {
                    putString("movieId", (movie.id).toString()) // Pass movie ID to detail screen
                }
            )
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }

        // Observe ViewModel for changes in movie list
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when {
                    state.isLoading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    state.error.isNotBlank() -> {
                        binding.progressBar.visibility = View.GONE
                        binding.errorText.text = state.error
                        binding.errorText.visibility = View.VISIBLE
                    }
                    state.movies.isNotEmpty() -> {
                        binding.progressBar.visibility = View.GONE
                        movieAdapter.submitList(state.movies)
                    }
                }
            }
        }

        binding.searchButton.setOnClickListener {
            Log.d("Fetch", "Button pressed")
            val query = binding.searchInput.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchMovies(query)
            } else {
                viewModel.getMovies() // If no query, fetch trending movies
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}