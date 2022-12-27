package com.asma.tasky.feature_management.presentation.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

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
            is EventEvent.ToggleShowAddAttendeeDialog -> {
                //todo
            }
            is EventEvent.AttendeeEmailEntered -> {
                //todo
            }
            is EventEvent.AttendeeEmailAdded -> {
                //todo
            }
            is EventEvent.AttendeeRemoved -> {
                //todo
            }
        }
    }

    private fun createEvent() {
        //todo create event
    }
}
