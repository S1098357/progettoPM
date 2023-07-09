import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.myapplication.TokenResponse

//Classe per contenere il Token di idealista
interface ApiService {
    @Headers("Content-Type: application/x-www-form-urlencoded",
        "Authorization: Basic aGs0anZrMzNmcnRieTE3d25qdzdndGFjOGU3ZXJpcGk6dWZnbWhhVVZaRHV0")
    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun getToken(
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String
    ): TokenResponse
}

object ApiClient {
    private const val BASE_URL = "https://api.idealista.com/"

    private val httpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

