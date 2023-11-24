package com.mongodb.app.presentation.tasks

import com.mongodb.app.domain.PriorityLevel
import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.mongodb.app.data.SyncRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class AddItemEvent {
    class Info(val message: String) : AddItemEvent()
    class Error(val message: String, val throwable: Throwable) : AddItemEvent()
}

open class AddItemViewModel(
    private val repository: SyncRepository
) : ViewModel() {

    private val _addItemPopupVisible: MutableState<Boolean> = mutableStateOf(false)
    val addItemPopupVisible: State<Boolean>
        get() = _addItemPopupVisible

    private val _taskSummary: MutableState<String> = mutableStateOf("")
    val ItemSummary: State<String>
        get() = _taskSummary

    private val _taskDescription: MutableState<String> = mutableStateOf("")
    val ItemDescription: State<String>
        get()= _taskDescription

    private val _location: MutableState<String> = mutableStateOf("")
    val location_: State<String>
        get()= _location

    private val _taskPriority: MutableState<PriorityLevel> = mutableStateOf(PriorityLevel.Tonight)
    val ItemPriority: State<PriorityLevel>
        get() = _taskPriority

    private val _imagestring: MutableState<String> = mutableStateOf("")
    val imagestring_: State<String>
        get()= _imagestring

    private val _expanded: MutableState<Boolean> = mutableStateOf(false)
    val expanded: State<Boolean>
        get() = _expanded

    private val _addItemEvent: MutableSharedFlow<AddItemEvent> = MutableSharedFlow()
    val addItemEvent: Flow<AddItemEvent>
        get() = _addItemEvent

    fun openAddTaskDialog() {
        _addItemPopupVisible.value = true
    }

    fun closeAddTaskDialog() {
        cleanUpAndClose()
    }

    fun updateTaskSummary(taskSummary: String) {
        _taskSummary.value = taskSummary
    }

    fun updateLocation(location: String){
        _location.value =location
    }

    fun updatePhoto(photo: String){
        _imagestring.value = photo
    }

    fun updateTaskDescription(taskDescription: String){
        _taskDescription.value = taskDescription
    }

    fun updateTaskPriority(taskPriority: PriorityLevel) {
        _taskPriority.value = taskPriority
    }
    fun open() {
        _expanded.value = true
    }
    fun close() {
        _expanded.value = false
    }

    fun addTask() {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                repository.addTask(ItemSummary.value, ItemDescription.value,ItemPriority.value, location_.value, imagestring_.value)
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    _addItemEvent.emit(AddItemEvent.Info("Item '$ItemSummary' with priority '$ItemPriority' added successfully."))
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    _addItemEvent.emit(AddItemEvent.Error("There was an error while adding the item '$ItemSummary'", it))
                }
            }
            cleanUpAndClose()
        }
    }

    private fun cleanUpAndClose() {
        _taskSummary.value = ""
        _taskDescription.value = ""
        _location.value = ""
        _imagestring.value = ""
        _taskPriority.value = PriorityLevel.Tonight
        _addItemPopupVisible.value = false
    }



    companion object {
        fun factory(
            repository: SyncRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ): AbstractSavedStateViewModelFactory {
            return object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return AddItemViewModel(repository) as T
                }
            }
        }
    }
}
