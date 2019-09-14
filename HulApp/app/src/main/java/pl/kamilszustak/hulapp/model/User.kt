package pl.kamilszustak.hulapp.model

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    var email: String,
    @SerializedName("first_name") var name: String,
    @SerializedName("last_name") var surname: String,
    @Embedded val profile: Profile?
) : ApplicationEntity() {
}