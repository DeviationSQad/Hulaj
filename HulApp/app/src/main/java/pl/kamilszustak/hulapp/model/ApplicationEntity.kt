package pl.kamilszustak.hulapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

abstract class ApplicationEntity : Parcelable {
    @PrimaryKey
    var id: Int = 0
}