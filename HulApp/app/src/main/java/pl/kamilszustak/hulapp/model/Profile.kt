package pl.kamilszustak.hulapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Profile(
    var name: String,
    var surname: String
) : Parcelable