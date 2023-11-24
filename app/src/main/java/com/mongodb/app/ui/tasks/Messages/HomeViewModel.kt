package com.mongodb.app.ui.tasks.Messages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import com.google.firestore.
import com.google.firebase.firestore.ktx.firestore
import java.lang.IllegalArgumentException

class HomeViewModel : ViewModel() {
    init {
        getMessages()
    }

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    private var _messages = MutableLiveData(emptyList<Map<String, Any>>().toMutableList())
    val messages: LiveData<MutableList<Map<String, Any>>> = _messages

    // Update the message value as user types
    fun updateMessage(message: String) {
        _message.value = message
    }

    fun addMessage() {
        val message: String = _message.value ?: throw IllegalArgumentException("message empty")
        if (message.isNotEmpty()) {
            Firebase.firestore.collection(Constants.MESSAGES).document().set(
                hashMapOf(
                    Constants.MESSAGE to message,
                    Constants.SENT_BY to Firebase.auth.currentUser?.uid,
                    Constants.SENT_ON to System.currentTimeMillis()
                )
            ).addOnSuccessListener {
                _message.value = ""
            }
        }
    }

    //Get messages
    private fun getMessages() {
        Firebase.firestore.collection(Constants.MESSAGES)
            //order based on time
            .orderBy(Constants.SENT_ON)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(Constants.TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val list = emptyList<Map<String, Any>>().toMutableList()
                if (value != null) {
                    for (doc in value) {
                        val data = doc.data
                        //authenticate user
                        data[Constants.IS_CURRENT_USER] =
                            Firebase.auth.currentUser?.uid.toString().equals(data[Constants.SENT_BY].toString())
                        //add message to the list to show it
                        list.add(data)
                    }
                }
                updateMessages(list)
            }
    }

    //Update the list after getting the details from firestore


    private fun updateMessages(list: MutableList<Map<String, Any>>) {
        _messages.value = list.asReversed()
    }

    private fun updateCurrent (list: MutableList<Map<String, Any>>){
        val size = list.size
//        _messages.value ? = list.asReversed()
    }
}