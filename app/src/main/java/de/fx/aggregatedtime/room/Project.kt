package de.fx.aggregatedtime.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Project(
    @PrimaryKey val uid: UUID,
    val name: String,
    val description: String?,
    val state: ProjectState,
)
