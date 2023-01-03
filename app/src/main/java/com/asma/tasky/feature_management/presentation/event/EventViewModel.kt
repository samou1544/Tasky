package com.asma.tasky.feature_management.presentation.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asma.tasky.core.domain.states.TextFieldState
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Resource
import com.asma.tasky.feature_authentication.domain.util.Validation
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.event.use_case.CreateEventUseCase
import com.asma.tasky.feature_management.domain.event.use_case.GetAttendeeUseCase
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.domain.util.Reminder
import com.asma.tasky.feature_management.domain.util.ReminderUtil
import com.asma.tasky.feature_management.presentation.event.util.AttendeeError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAttendeeUseCase: GetAttendeeUseCase,
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {

    private val currentUserId = ""
    private val _eventState = MutableStateFlow(EventState())
    val eventState = _eventState.asStateFlow()

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
            // this is a new event, turn on editable mode
            _eventState.update {
                it.copy(isEditable = true)
            }
        }
    }

    private fun updateState(event: AgendaItem.Event) {
        _eventState.update {
            it.copy(event = event)
        }
        if (event.eventCreator == currentUserId)
            _eventState.update {
                it.copy(showDeleteEvent = true)
            }
        event.startDate.let { time ->
            _eventState.update {
                it.copy(startTime = DateUtil.secondsToLocalDateTime(time))
            }
        }
        _eventState.update {
            it.copy(
                reminder = ReminderUtil.computeReminder(
                    startTime = event.startDate,
                    reminderTime = event.reminder
                )
            )
        }
    }

    fun onEvent(event: EventEvent) {
        when (event) {
            is EventEvent.PhotosAdded -> {
                _eventState.update {
                    it.copy(photos = it.photos + event.uriList)
                }
            }
            is EventEvent.PhotoDeleted -> {
                _eventState.update {
                    it.copy(photos = it.photos.minus(event.uri))
                }

            }
            is EventEvent.TitleEntered -> {
                _eventState.update {
                    it.copy(event = it.event.copy(eventTitle = event.title))
                }
            }
            is EventEvent.DescriptionEntered -> {
                _eventState.update {
                    it.copy(event = it.event.copy(eventDescription = event.description))
                }
            }
            is EventEvent.ReminderSelected -> {
                _eventState.update {
                    it.copy(reminder = event.reminder)
                }
            }
            is EventEvent.Save -> {
                _eventState.update {
                    it.copy(isLoading = true)
                }
                createEvent()
            }
            is EventEvent.Delete -> {
                //todo delete event
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
            is EventEvent.StartTimeSelected -> {
                _eventState.update {
                    it.copy(startTime = it.startTime.with(event.time))
                }
            }
            is EventEvent.StartDateSelected -> {
                _eventState.update {
                    it.copy(startTime = it.startTime.with(event.date))
                }
            }
            is EventEvent.EndTimeSelected -> {
                _eventState.update {
                    it.copy(endTime = it.endTime.with(event.time))
                }
            }
            is EventEvent.EndDateSelected -> {
                _eventState.update {
                    it.copy(endTime = it.endTime.with(event.date))
                }
            }
            is EventEvent.ChangeStatus -> {
                _eventState.update {
                    it.copy(selectedAttendeeStatus = event.status)
                }
            }
            is EventEvent.ToggleShowAddAttendeeDialog -> {
                _eventState.update {
                    it.copy(showAddAttendeeDialog = it.showAddAttendeeDialog.not())
                }
                resetAttendeeDialogState()
            }
            is EventEvent.AttendeeEmailEntered -> {
                _eventState.update {
                    it.copy(attendeeEmail = TextFieldState(text = event.email))
                }
                validateAttendeeEmail(event.email)
            }
            is EventEvent.AttendeeEmailAdded -> {
                _eventState.update {
                    it.copy(isCheckingAttendeeEmail = true)
                }
                getAttendee(event.email)
            }
            is EventEvent.AttendeeRemoved -> {
                _eventState.update { state ->
                    state.copy(
                        event = state.event.copy(attendees = state.event.attendees - event.attendee)
                    )
                }
            }
        }
    }

    private fun computeReminderSeconds(reminder: Reminder, startTime: LocalDateTime): Long {
        return DateUtil.localDateTimeToSeconds(startTime) - reminder.seconds
    }

    private fun validateAttendeeEmail(email: String) {
        val emailError = Validation.validateEmail(email)
        _eventState.update {
            it.copy(
                attendeeEmail = it.attendeeEmail.copy(error = emailError),
                isAttendeeEmailValid = emailError == null
            )
        }
    }

    private fun getAttendee(email: String) {
        viewModelScope.launch {
            when (val result = getAttendeeUseCase(email)) {
                is Resource.Success -> {
                    _eventState.update { state ->
                        state.copy(
                            event = state.event.copy(attendees = state.event.attendees + result.data!!)
                        )
                    }
                    resetAttendeeDialogState()
                }
                is Resource.Error -> {

                    _eventState.update {
                        it.copy(
                            isCheckingAttendeeEmail = false,
                            attendeeEmail = it.attendeeEmail.copy(error = AttendeeError.NoUserFound)
                        )
                    }
                }
            }
        }
    }

    private fun resetAttendeeDialogState() {
        _eventState.update {
            it.copy(
                attendeeEmail = TextFieldState(),
                isAttendeeEmailValid = null,
                isCheckingAttendeeEmail = false
            )
        }
    }

    private fun createEvent() {
        viewModelScope.launch {
            when (createEventUseCase(
                event = _eventState.value.event.copy(
                    eventStartDate = DateUtil.localDateTimeToSeconds(
                        _eventState.value.startTime
                    ),
                    eventEndDate = DateUtil.localDateTimeToSeconds(
                        _eventState.value.endTime
                    )
                ),
                photos = _eventState.value.photos.map { uri ->
                    uri.toString()
                })) {
                is Resource.Error -> {

                }
                is Resource.Success -> {
                    _eventFlow.emit(UiEvent.NavigateUp)
                }
            }

        }
    }
}
