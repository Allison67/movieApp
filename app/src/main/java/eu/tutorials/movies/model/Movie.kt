package eu.tutorials.movies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String
): Parcelable

data class MoviesResponse(val movies: List<Movie>)