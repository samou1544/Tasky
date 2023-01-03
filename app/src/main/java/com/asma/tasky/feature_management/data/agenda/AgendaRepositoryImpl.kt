package com.asma.tasky.feature_management.data.agenda

import com.asma.tasky.core.domain.auth.AuthResult
import com.asma.tasky.core.util.UiText
import com.asma.tasky.feature_management.data.event.local.EventDao
import com.asma.tasky.feature_management.data.event.local.ModifiedEventEntity
import com.asma.tasky.feature_management.data.event.remote.EventApi
import com.asma.tasky.feature_management.data.event.toAgendaEvent
import com.asma.tasky.feature_management.data.event.toCreateEventRequest
import com.asma.tasky.feature_management.data.reminder.local.ReminderDao
import com.asma.tasky.feature_management.data.reminder.local.toAgendaReminder
import com.asma.tasky.feature_management.data.task.local.TaskDao
import com.asma.tasky.feature_management.data.task.local.toAgendaTask
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.agenda.repository.AgendaRepository
import com.asma.tasky.feature_management.domain.util.CacheResult
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.domain.util.ModificationType
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoField

class AgendaRepositoryImpl(
    private val taskDao: TaskDao,
    private val eventDao: EventDao,
    private val reminderDao: ReminderDao,
    private val eventApi: EventApi
) :
    AgendaRepository {

    override fun getAgendaItems(
        day: LocalDate,
        cacheOnly: Boolean
    ): Flow<CacheResult<List<AgendaItem>>> = flow {
        emit(CacheResult.Local(getLocalAgenda(day)))
        if (cacheOnly) return@flow

        // sync cache
        when (val result = syncAgenda()) {
            is AuthResult.Error -> emit(CacheResult.Error(result.message ?: UiText.unknownError()))
            is AuthResult.Success -> emit(CacheResult.Remote(result.data ?: emptyList()))
            is AuthResult.Unauthorized -> emit(CacheResult.Unauthorized())
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

    private suspend fun syncAgenda(): AuthResult<List<AgendaItem>> = supervisorScope {

        val modifiedEventsDeferred = async { eventDao.getModifiedEvents() }
        val modifiedTasksDeferred = async { taskDao.getModifiedTasks() }
        val modifiedRemindersDeferred = async { reminderDao.getModifiedReminders() }

        val modifiedEvents = modifiedEventsDeferred.await()

        val createdEvents = modifiedEvents.filter {
            it.modificationType == ModificationType.Created.value
        }

        val updatedEvents = modifiedEvents.filter {
            it.modificationType == ModificationType.Updated.value
        }

        val deletedEvents = modifiedEvents.filter {
            it.modificationType == ModificationType.Deleted.value
        }


        val modifiedTasks = modifiedTasksDeferred.await()
        val modifiedReminders = modifiedRemindersDeferred.await()

        // sync created and updated Items

        // cal api.sync agenda

        // clear deleted items

        // get AgendaItems from remote

        AuthResult.Success(emptyList())
    }

    private fun updateLocalAgenda(items: List<AgendaItem>) {
    }

    private suspend fun syncCreatedEvents(createdEvents: List<ModifiedEventEntity>) =
        supervisorScope {

            createdEvents.map { event ->
                launch {
                    eventDao.getEventById(event.id)?.toAgendaEvent()?.toCreateEventRequest()
                        ?.let { request ->
                            eventApi.createEvent(
                                eventData = MultipartBody.Part
                                    .createFormData(
                                        "create_event_request",
                                        Gson().toJson(request)
                                    ),
                                eventPhotos = emptyList()
                            )

                            eventDao.deleteModifiedEvent(modifiedEvent = event)
                        }
                }
            }.forEach {
                it.join()
            }

        }

    private suspend fun syncUpdatedEvents(createdEvents: List<ModifiedEventEntity>) =
        supervisorScope {

            createdEvents.map { event ->
                launch {
                    //todo should be to update event request
                    eventDao.getEventById(event.id)?.toAgendaEvent()?.toCreateEventRequest()
                }
            }.forEach {
                it.join()
            }

        }
}
