package eu.tutorials.movies.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail (
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String
    // can add more features in the detail page
): Parcelable