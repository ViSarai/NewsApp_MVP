package daniellopes.io.newsappstarter.network

import daniellopes.io.newsappstarter.model.NewsResponse
import daniellopes.io.newsappstarter.util.Constans.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query ("page")
        pageNumber: Number = 1,
        @Query ("apiKey")
        apiKey: String = API_KEY

    ):Response<NewsResponse>

    @GET ("/v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query ("page")
        pageNumber: Number = 1,
        @Query ("apiKey")
        apiKey: String = API_KEY

    ):Response<NewsResponse>
}