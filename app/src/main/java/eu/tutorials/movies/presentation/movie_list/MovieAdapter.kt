package eu.tutorials.movies.presentation.movie_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import eu.tutorials.movies.R
import eu.tutorials.movies.common.Constants
import eu.tutorials.movies.domain.model.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        Log.d("MovieAdapter", "Binding movie at position $position: ${movie.title}")

        holder.bind(movie)
        holder.itemView.setOnClickListener { onMovieClick(movie) }
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            moviePoster.load(Constants.POSTER_BASE_URL + movie.posterPath)
        }

    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}