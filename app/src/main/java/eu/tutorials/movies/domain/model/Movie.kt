package eu.tutorials.movies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    // overview can be removed here
    val overview: String,
    val posterPath: String
): Parcelable
