package pl.kamilszustak.hulapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Profile(
    var birthDate: Date = Date()
) : Parcelable