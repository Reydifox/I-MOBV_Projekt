package sk.stuba.fei.i_mobv_projekt.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val URL = "https://data.mongodb-api.com/app/data-fswjp/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(URL)
    .build()

data class PubRequest
(
    val collection: String,
    val database : String,
    val dataSource : String
)

interface ApiServices
{
    @Headers("api-key: KHUu1Fo8042UwzczKz9nNeuVOsg2T4ClIfhndD2Su0G0LHHCBf0LnUF05L231J0M",
    "Access-Control-Rquest-Headers: *",
    "Content-Type: application/json")
    @POST("endpoint/data/v1/action/find")
    suspend fun getJsonString(@Body request: PubRequest): String
}

object Api
{
    val retrofitService: ApiServices by lazy { retrofit.create(ApiServices::class.java) }
}