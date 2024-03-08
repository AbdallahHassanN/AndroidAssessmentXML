package com.example.technical_assignment_xml.domain.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.RawValue

@Entity
@Parcelize
data class StoreItem(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: String,
    val price: Double,
    @Embedded
    val rating: @RawValue Rating,
    val title: String
): Parcelable