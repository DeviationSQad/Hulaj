package pl.kamilszustak.hulapp.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
    var email: String,
    var passwordHash: String,
    @Embedded val profile: Profile
) : ApplicationEntity() {
}