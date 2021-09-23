package de.fx.aggregatedtime.room

import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectDao: ProjectDao) {
    fun projectDao() : ProjectDao {
        return projectDao
    }
}