package de.fx.aggregatedtime.room

import androidx.room.*
import java.util.*

@Dao
interface ProjectDao {
    @Query("SELECT * from Project")
    fun getAll(): List<Project>

    @Query("SELECT * FROM Project WHERE uid IS :uid")
    fun loadById(uid: UUID): Project

    @Insert
    fun insert(project: Project)

    @Delete
    fun delete(project: Project)

    @Transaction
    @Query("SELECT * FROM Project")
    fun getProjectsWithTimeAggregates(): List<ProjectWithTimeAggregates>
}