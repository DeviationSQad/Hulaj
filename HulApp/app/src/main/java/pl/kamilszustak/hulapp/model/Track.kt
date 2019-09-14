package pl.kamilszustak.hulapp.model

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "track")
data class Track(
    val startTime: Date,
    val endTime: Date,
    val length: Double
) : ApplicationEntity() {

}