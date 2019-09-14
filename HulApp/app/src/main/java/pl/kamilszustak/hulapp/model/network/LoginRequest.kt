package pl.kamilszustak.hulapp.model.network

data class LoginRequest(
    private val email: String,
    private val password: String
)