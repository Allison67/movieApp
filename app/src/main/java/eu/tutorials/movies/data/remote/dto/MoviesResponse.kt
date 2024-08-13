package eu.tutorials.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val results: List<MovieDto>
)