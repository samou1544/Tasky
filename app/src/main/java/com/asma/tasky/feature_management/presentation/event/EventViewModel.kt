package com.asma.tasky.feature_management.presentation.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.Constants
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.task.use_case.AddTaskUseCase
import com.asma.tasky.feature_management.domain.task.use_case.DeleteTaskUseCase
import com.asma.tasky.feature_management.domain.task.use_case.GetTaskUseCase
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.domain.util.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventPhotos = MutableStateFlow<List<String>>(emptyList())
    val eventPhotos = _eventPhotos.asStateFlow()

    private val _eventState = MutableStateFlow(EventState())
    val eventState = _eventState.asStateFlow()

    private val _eventTime = MutableStateFlow(LocalDateTime.now())
    val eventTime = _eventTime.asStateFlow()

    private val _eventReminder = MutableStateFlow<Reminder>(Reminder.OneHourBefore)
    val eventReminder = _eventReminder.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Boolean>(Constants.PARAM_EDITABLE)?.let { editable ->
            _eventState.update {
                it.copy(isEditable = editable)
            }
        }

        val eventId = savedStateHandle.get<String>(Constants.PARAM_ID)

        if (!eventId.isNullOrEmpty()) {
            // todo get event
        } else {
            // this is a new task, turn on editable mode
            _eventState.update {
                it.copy(isEditable = true)
            }
        }
    }

    private fun updateState(event: AgendaItem.Event) {
        _eventState.update {
            it.copy(event = event)
        }
        _eventState.update {
            it.copy(showDeleteEvent = true)
        }
        event.startDate.let { time ->
            _eventTime.update { DateUtil.secondsToLocalDateTime(time) }
        }
        _eventReminder.update {
            computeReminder(
                startTime = event.startDate,
                reminderTime = event.reminder
            )
        }
    }

    fun onEvent(event: EventEvent) {
        when (event) {
            is EventEvent.PhotosAdded -> {
                _eventPhotos.update {
                    it + event.uriList
                }
            }
            is EventEvent.PhotoDeleted -> {
                _eventPhotos.update {
                    it.minus(event.uri)
                }

            }
            is EventEvent.TitleEntered -> {
                _eventState.update {
                    it.copy(event = it.event.copy(title = event.title))
                }
            }
            is EventEvent.DescriptionEntered -> {
                _eventState.update {
                    it.copy(event = it.event.copy(description = event.description))
                }
            }
            is EventEvent.TimeSelected -> {
                _eventTime.update {
                    it.with(event.time)
                }
            }
            is EventEvent.DateSelected -> {
                _eventTime.update {
                    it.with(event.date)
                }
            }
            is EventEvent.ReminderSelected -> {
                _eventReminder.update {
                    event.reminder
                }
            }
            is EventEvent.Save -> {
                _eventState.update {
                    it.copy(isLoading = true)
                }
            }
            is EventEvent.Delete -> {
            }
            is EventEvent.ToggleReminderDropDown -> {
                _eventState.update {
                    it.copy(showReminderDropDown = it.showReminderDropDown.not())
                }
            }
            is EventEvent.ToggleEditMode -> {
                _eventState.update {
                    it.copy(isEditable = it.isEditable.not())
                }
            }
        }
    }

    private fun computeReminderSeconds(reminder: Reminder, startTime: LocalDateTime): Long {
        return DateUtil.localDateTimeToSeconds(startTime) - reminder.seconds
    }

    private fun computeReminder(reminderTime: Long, startTime: Long): Reminder {
        return when (startTime - reminderTime) {
            Reminder.OneHourBefore.seconds -> {
                Reminder.OneHourBefore
            }
            Reminder.OneDayBefore.seconds -> {
                Reminder.OneDayBefore
            }
            Reminder.ThirtyMinutesBefore.seconds -> {
                Reminder.ThirtyMinutesBefore
            }
            Reminder.TenMinutesBefore.seconds -> {
                Reminder.TenMinutesBefore
            }
            Reminder.SixHoursBefore.seconds -> {
                Reminder.SixHoursBefore
            }
            else -> {
                Reminder.OneHourBefore
            }
        }
    }
}
