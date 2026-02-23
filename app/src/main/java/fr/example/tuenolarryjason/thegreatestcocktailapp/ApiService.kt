package fr.example.tuenolarryjason.thegreatestcocktailapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun getRandomCocktail(): DrinkDetailResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinkListResponse

    @GET("lookup.php")
    suspend fun getDrinkById(@Query("i") id: String): DrinkDetailResponse
}

object NetworkManager {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
