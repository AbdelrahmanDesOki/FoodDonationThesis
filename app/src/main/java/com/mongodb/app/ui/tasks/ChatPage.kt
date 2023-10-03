package com.mongodb.app.ui.tasks


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class ChatMessage(val text: String, val isUser: Boolean)


@Composable
fun ChatPage() {
    val messages by remember { mutableStateOf(dummyData()) }
    var newMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Text(
            text = "Chat Page",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom,
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                ChatBubble(
                    message = message.text,
                    isUser = message.isUser,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = newMessage,
                onValueChange = { newMessage = it },
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (newMessage.isNotBlank()) {
                            messages.add(ChatMessage(newMessage, true))
                            newMessage = ""
                        }
                    }
                ),
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )

            IconButton(
                onClick = {
                    if (newMessage.isNotBlank()) {
                        messages.add(ChatMessage(newMessage, true))
                        newMessage = ""
                    }
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: String,
    isUser: Boolean,
) {
    val bubbleColor = if (isUser) Color.Blue else Color.Gray
    val bubbleAlignment = if (isUser) Alignment.End else Alignment.Start
    val alignment = if (isUser) Alignment.End else Alignment.Start

    Box(
        modifier = Modifier
            .fillMaxWidth()
//            .align(bubbleAlignment)
//            .align(bubbleAlignment)
            .padding(8.dp)
//        ,
//                contentAlignment = alignment

    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
//                .elevation(4.dp)
                .padding(8.dp)
//            elevation = 4.dp
        ) {
            Text(
                text = message,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentWidth()
                    .background(bubbleColor)
            )
        }
    }
}

fun dummyData(): MutableList<ChatMessage> {
    val messages = mutableListOf<ChatMessage>()
    val random = Random(System.currentTimeMillis())

    repeat(10) {
        val isUser = random.nextBoolean()
        val message = if (isUser) {
            "User message $it"
        } else {
            "Other user message $it"
        }
        messages.add(ChatMessage(message, isUser))
    }

    return messages
}

@Preview
@Composable
fun ChatPagePreview() {
    ChatPage()
}
