package pl.kamilszustak.hulapp.model.network

import com.google.gson.annotations.SerializedName
import pl.kamilszustak.hulapp.model.User

class LoginResponse(
    @SerializedName("key") val token: String,
    @SerializedName("user") val user: User
)