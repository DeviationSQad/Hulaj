package pl.kamilszustak.hulapp.model

import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "event")
data class Event(
    var title: String,
    var description: String
) : ApplicationEntity()