package eu.tutorials.movies.presentation.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import eu.tutorials.movies.common.Constants
import eu.tutorials.movies.databinding.FragmentMovieDetailBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Collect the ViewModel's StateFlow using lifecycleScope
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                state.movie?.let { movie ->
                    binding.movieTitle.text = movie.title
                    binding.moviePoster.load(Constants.POSTER_BASE_URL + movie.posterPath)
                    binding.movieOverview.text = movie.overview
                }

                // Handle loading state
                binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

                // Handle error state
                if (state.error.isNotBlank()) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.error
                } else {
                    binding.errorText.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
