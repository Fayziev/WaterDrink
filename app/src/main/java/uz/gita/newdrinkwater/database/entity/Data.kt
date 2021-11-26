package uz.gita.newdrinkwater.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Data(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val time: String,
    val size: String
)