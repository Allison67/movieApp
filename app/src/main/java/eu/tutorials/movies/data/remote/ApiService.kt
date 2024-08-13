package eu.tutorials.movies.data.remote

import eu.tutorials.movies.data.remote.dto.MovieDetailDto
import eu.tutorials.movies.data.remote.dto.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/day")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ZjZjNGM1MzExODVmZTY3N2I1MzFkOGU5YWJjZGYwZSIsIm5iZiI6MTcyMjQ0OTQzMS43ODIyMDYsInN1YiI6IjY2YWE3ZDVlYjAyMjIyZjYyMGUzYzFlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kOLnNnq6WCv2wjNqydPWhnDEOXJJg8-xpIN8SWhtbfw")
    suspend fun getTrendingMovies(@Query("language") language: String = "en-US"): MoviesResponse

    @GET("search/movie")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ZjZjNGM1MzExODVmZTY3N2I1MzFkOGU5YWJjZGYwZSIsIm5iZiI6MTcyMjQ0OTQzMS43ODIyMDYsInN1YiI6IjY2YWE3ZDVlYjAyMjIyZjYyMGUzYzFlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kOLnNnq6WCv2wjNqydPWhnDEOXJJg8-xpIN8SWhtbfw")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MoviesResponse

    @GET("movie/{movie_id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ZjZjNGM1MzExODVmZTY3N2I1MzFkOGU5YWJjZGYwZSIsIm5iZiI6MTcyMjQ2Mjc4OC4yNzI1OTgsInN1YiI6IjY2YWE3ZDVlYjAyMjIyZjYyMGUzYzFlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.uw04SbyPK9KMtkAfa3cUeMsRJkxuJGFmxDo7shMnaag")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto

}
