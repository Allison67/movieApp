package eu.tutorials.movies

import eu.tutorials.movies.model.Movie
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

class ApiService {
    private val client = OkHttpClient()

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private val AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0ZjZjNGM1MzExODVmZTY3N2I1MzFkOGU5YWJjZGYwZSIsIm5iZiI6MTcyMjQ0OTQzMS43ODIyMDYsInN1YiI6IjY2YWE3ZDVlYjAyMjIyZjYyMGUzYzFlMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kOLnNnq6WCv2wjNqydPWhnDEOXJJg8-xpIN8SWhtbfw"


    fun getTrendingMovies(): List<Movie> {
        val request = Request.Builder()
            .url(BASE_URL+"trending/movie/day?language=en-US")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", AUTHORIZATION)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body?.string() ?: throw IOException("Empty response body")
            val jsonObject = JSONObject(responseBody)
            val results = jsonObject.getJSONArray("results")

            val movies = mutableListOf<Movie>()
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val movie = Movie(
                    id = item.getInt("id"),
                    title = item.getString("title"),
                    overview = item.getString("overview"),
                    poster_path = "https://image.tmdb.org/t/p/w500" + item.getString("poster_path")
                )
                movies.add(movie)
            }
            return movies
        }
    }

    fun searchMovies(query: String): List<Movie> {
        val request = Request.Builder()
            .url("$BASE_URL/search/movie?query=$query&include_adult=false&language=en-US&page=1")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", AUTHORIZATION)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body?.string() ?: throw IOException("Empty response body")
            val jsonObject = JSONObject(responseBody)
            val results = jsonObject.getJSONArray("results")

            val movies = mutableListOf<Movie>()
            for (i in 0 until results.length()) {
                val item = results.getJSONObject(i)
                val movie = Movie(
                    id = item.getInt("id"),
                    title = item.getString("title"),
                    overview = item.getString("overview"),
                    poster_path = "https://image.tmdb.org/t/p/w500" + item.getString("poster_path")
                )
                movies.add(movie)
            }
            return movies
        }
    }
}
