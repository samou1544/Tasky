package com.asma.tasky.feature_management.data.agenda

import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.toAgendaEvent
import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.reminder.local.toAgendaReminder
import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.toAgendaTask
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.DateUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        emit(getLocalAgenda(day))

        syncAgenda()

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

    private fun syncAgenda() {
        //todo get modified agenda items and sync them with remote
    }

}
