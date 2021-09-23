package de.fx.aggregatedtime.room

import androidx.room.Embedded
import androidx.room.Relation

data class ProjectWithTimeAggregates(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "uid",
        entityColumn = "projectId"
    )
    val timeAggregates: List<TimeAggregate>
)