package pl.kamilszustak.hulapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Profile(
    @SerializedName("date_of_birth") var birthDate: Date = Date(),
    @SerializedName("photo") var photoUrl: String = "",
    var country: String = "",
    var city: String = "",
    var bio: String = "",
    var points: Int = 0
) : Parcelable