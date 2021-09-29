package de.fx.aggregatedtime.room

import androidx.room.*
import java.time.LocalDate
import java.util.*

@Dao
interface ProjectDao {
    @Query("SELECT * from Project")
    fun getAll(): List<Project>

    @Query("SELECT * FROM Project WHERE uid IS :uid")
    fun loadById(uid: UUID): Project

    @Insert
    fun insert(project: Project)

    @Update
    fun update(project: Project)

    @Delete
    fun delete(project: Project)

    @Query("SELECT * FROM Project p WHERE p.state == 'ACTIVE'")
    fun getActiveProjects(): List<Project>

    @Transaction
    @Query("SELECT * FROM Project")
    fun getProjectsWithTimeAggregates(): List<ProjectWithTimeAggregates>

    @Insert
    fun insert(timeAggregate: TimeAggregate)

    @Update
    fun update(timeAggregate: TimeAggregate)

    @Query("SELECT * FROM TimeAggregate t WHERE date == :date")
    fun getTimeAggregates(date: LocalDate): List<TimeAggregate>

    @Query("SELECT * FROM TimeAggregate t WHERE t.projectId == :projectId AND date == :date")
    fun getTimeAggregate(projectId: UUID, date: LocalDate): TimeAggregate

    @Query("SELECT EXISTS(SELECT * FROM TimeAggregate t WHERE t.projectId == :projectId AND date == :date)")
    fun hasTimeAggregate(projectId: UUID, date: LocalDate): Boolean
}