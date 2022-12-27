package com.asma.tasky.feature_management.data.agenda

import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.ModifiedEventEntity
import com.asma.tasky.feature_management.data.event.local.toAgendaEvent
import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.reminder.local.toAgendaReminder
import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.toAgendaTask
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.domain.util.ModificationType
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoField
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.supervisorScope

class AgendaRepositoryImpl(
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val reminderDao: ReminderDao,
) :
    AgendaRepository {

    override fun getAgendaItems(day: LocalDate): Flow<List<AgendaItem>> = flow {
        emit(getLocalAgenda(day))

        // sync cache
        when (syncAgenda()) {
            is Resource.Error -> {}
            is Resource.Success -> {
            }
        }
    }

    private suspend fun getLocalAgenda(day: LocalDate) = supervisorScope {
        val endOfDay = day.atStartOfDay().with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay())
        val eventsDeferred = async {
            eventDao.getEventsOfTheDay(
                startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
                endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
            )
        }
        val tasksDeferred = async {
            taskDao.getTasksOfTheDay(
                startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
                endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
            )
        }
        val remindersDeferred = async {
            reminderDao.getRemindersOfTheDay(
                startOfDay = DateUtil.localDateTimeToSeconds(day.atStartOfDay()),
                endOfDay = DateUtil.localDateTimeToSeconds(endOfDay)
            )
        }

        val tasks = tasksDeferred.await().map { it.toAgendaTask() }
        val events = eventsDeferred.await().map { it.toAgendaEvent() }
        val reminders = remindersDeferred.await().map { it.toAgendaReminder() }

        (events + tasks + reminders).sortedBy { it.startDate }
    }

    private suspend fun syncAgenda(): Resource<List<AgendaItem>> = supervisorScope {

        val modifiedEventsDeferred = async { eventDao.getModifiedEvents() }
        val modifiedTasksDeferred = async { taskDao.getModifiedTasks() }
        val modifiedRemindersDeferred = async { reminderDao.getModifiedReminders() }

        val modifiedEvents = modifiedEventsDeferred.await().map {
        }
        val modifiedTasks = modifiedTasksDeferred.await()
        val modifiedReminders = modifiedRemindersDeferred.await()

        // sync created and updated Items

        // cal api.sync agenda

        // clear deleted items

        // get AgendaItems from remote

        Resource.Success(emptyList())
    }

    private fun updateLocalAgenda(items: List<AgendaItem>) {
    }

    private fun syncModifiedEvents(modifiedEvents: List<ModifiedEventEntity>) {
        val createdEvents = modifiedEvents.filter {
            it.modificationType == ModificationType.Created.value
        }

        val updatedEvents = modifiedEvents.filter {
            it.modificationType == ModificationType.Updated.value
        }
    }
}
