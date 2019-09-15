package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.model.Event
import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.network.LoginRequest
import pl.kamilszustak.hulapp.model.network.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HulAppService {

    @POST("/api/auth/login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/logout/")
    suspend fun logout(): Response<Unit>

    @POST("/api/tracks")
    suspend fun postTrack(@Body track: Track): Response<Track>

    @GET("/api/tracks")
    suspend fun getAllTracks(): Response<List<Track>>

    @GET("/api/tracks")
    suspend fun getTracksByUserId(@Query("id_user") userId: Int): Response<List<Track>>

    @GET("/api/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @POST("/api/events")
    suspend fun postEvent(@Body event: Event): Response<Event>
}