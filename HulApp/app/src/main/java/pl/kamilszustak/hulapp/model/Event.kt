package pl.kamilszustak.hulapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "event")
data class Event(
    var title: String = "",
    var description: String = "",
    @SerializedName("place_name") var placeName: String = "",
    var country: String = "",
    var city: String = "",
    var address: String = "",
    @SerializedName("event_date") var date: Date = Date(),
    @SerializedName("max_amount_of_people") var maxAmountOfPeople: Int = 0,
    @SerializedName("is_active") var isActive: Boolean = true,
    @SerializedName("id_user") var userId: Int = 0
) : ApplicationEntity()