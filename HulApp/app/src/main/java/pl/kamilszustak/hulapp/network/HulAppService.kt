package pl.kamilszustak.hulapp.network

import pl.kamilszustak.hulapp.model.Track
import pl.kamilszustak.hulapp.model.network.LoginRequest
import pl.kamilszustak.hulapp.model.network.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface HulAppService {

    @POST("/api/auth/login/")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("/api/track/")
    suspend fun postTrack(@Body track: Track): Response<Unit>
}