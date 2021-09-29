package de.fx.aggregatedtime.room

import androidx.annotation.NonNull
import androidx.room.Entity
import java.time.LocalDate
import java.util.*

@Entity(primaryKeys = arrayOf("projectId","date"))
data class TimeAggregate(
    @NonNull val projectId: UUID,
    @NonNull val date: LocalDate,
    var minutes: Int,
)
