package pl.kamilszustak.hulapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(
    tableName = "track",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Track(
    @SerializedName("time_start") var startTime: Date = Date(),
    @SerializedName("time_end") var endTime: Date = Date(),
    var duration: Double = 0.0,
    @SerializedName("track_length") var length: Double = 0.0,
    @SerializedName("id_user") var userId: Int = 0
) : ApplicationEntity()