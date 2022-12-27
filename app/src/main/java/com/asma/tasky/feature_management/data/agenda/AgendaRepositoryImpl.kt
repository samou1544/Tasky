package com.asma.tasky.feature_management.data.agenda

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.toAgendaEvent
import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.toAgendaTask
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.DateUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoField

class AgendaRepositoryImpl(
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val reminderDao: ReminderDao
) :
    AgendaRepository {

    override fun getAgendaItems(day: LocalDate): Flow<List<AgendaItem>> = flow {
        getLocalAgenda(day).collect { items ->
            emit(items)
        }

        //sync cache
        when (syncAgenda()) {
            is Resource.Error -> {}
            is Resource.Success -> {

            }
        }


    }

    private fun getLocalAgenda(day: LocalDate): Flow<List<AgendaItem>> {
        val endOfDay = day.atStartOfDay().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay())
        val tasksFlow = taskDao.getTasksOfTheDay(
            startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
            endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
        )
            .map { list ->
                list.map { it.toAgendaTask() }
            }

        val eventsFlow = eventDao.getEventsOfTheDay(
            startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
            endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
        ).map { events ->
            events.map {
                it.toAgendaEvent()
            }
        }

        return tasksFlow.combine(eventsFlow) { tasks, events ->
            tasks + events
        }
    }

    private suspend fun syncAgenda(): Resource<List<AgendaItem>> = supervisorScope {

        val modifiedEventsDeferred = async { eventDao.getModifiedEvents() }
        val modifiedTasksDeferred = async { taskDao.getModifiedTasks() }
        val modifiedRemindersDeferred = async { reminderDao.getModifiedReminders() }

        val modifiedEvents = modifiedEventsDeferred.await()
        //sync created and updated Items

        //cal api.sync agenda

        //clear deleted items

        //get AgendaItems from remote

        Resource.Success(emptyList())
    }

    private fun updateLocalAgenda(items: List<AgendaItem>) {


    }
}
