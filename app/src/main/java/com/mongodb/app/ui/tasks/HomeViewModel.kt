package com.mongodb.app.ui.tasks

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

    val stringList = mutableListOf<String>()


    //Send message

    fun addMessage() {
        val message: String = _message.value ?: throw IllegalArgumentException("message empty")
        if (message.isNotEmpty()) {
            stringList.add(Constants.SENT_BY)
//            stringList.add("ypzp89swD0TMK7IkZ478SHPVyqx1")
//            stringList.add("00ipDG3juaSEXweQVv92KwHCrwg1")

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
                        data[Constants.IS_CURRENT_USER] =
                            Firebase.auth.currentUser?.uid.toString() == data[Constants.SENT_BY].toString()

                        list.add(data)
                    }
                }
// Issue now is: Everyone can see the messages.
                for (item in stringList) {
                    if (item == Firebase.auth.currentUser?.uid.toString()) {
                        updateMessages(list)
//                        Log.d("test", item)
                        break
                    }
                }

                updateMessages(list)


            }
    }

    //Update the list after getting the details from firestore

    private fun updateMessages(list: MutableList<Map<String, Any>>) {

        _messages.value = list.asReversed()
    }
}