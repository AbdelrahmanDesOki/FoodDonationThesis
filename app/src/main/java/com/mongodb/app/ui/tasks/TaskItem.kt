package com.mongodb.app.ui.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mongodb.app.R
import com.mongodb.app.data.MockRepository
import com.mongodb.app.data.SyncRepository
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.ItemContextualMenuViewModel
import com.mongodb.app.presentation.tasks.TaskViewModel
import com.mongodb.app.ui.theme.Blue
import com.mongodb.app.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.MutableSharedFlow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun TaskItem(
    taskViewModel: TaskViewModel,
    itemContextualMenuViewModel: ItemContextualMenuViewModel,
    task: Item,

) {
    var chatTabOpened by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Guard against modifying some else's task - sync error callback would catch it though
//        Checkbox(
//            checked = task.isComplete,
//            onCheckedChange = {
//                if (taskViewModel.isTaskMine(task)) {
//                    taskViewModel.toggleIsComplete(task)
//                } else {
//                    taskViewModel.showPermissionsMessage()
//                }
//            }
//        )
//
//     Button(onClick = { /*TODO*/ },
//         modifier = Modifier
//             .padding( 6.dp)
//             .size(55.dp)
//
//     ) {
//         Text(text = "📩",
//             Modifier
//                 .size(60.dp)
//                 .fillMaxWidth()
//                 .align(Alignment.CenterVertically))
//     }
        ChatButton(onClick = { chatTabOpened = true })


        Column {
            Text(
                text = task.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = Blue,
                fontWeight = FontWeight.Bold
            )
            // Ownership text visible only if task is mine
            if (taskViewModel.isTaskMine(task)) {
                Text(
                    text = stringResource(R.string.mine),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        // Delete icon
//        if (repository.isTaskMine(task)) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            ItemContextualMenu(itemContextualMenuViewModel, task)
        }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    MyApplicationTheme {
        val repository = MockRepository()
        val taskViewModel = TaskViewModel(repository)
        TaskItem(
            taskViewModel,
            ItemContextualMenuViewModel(repository, taskViewModel),
            MockRepository.getMockTask(42)
        )
    }
}

@Composable
fun ChatButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(4.dp)
            .background(Color.White, shape = CircleShape),

//         PaddingValues(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Send,
            contentDescription = null, // Provide a meaningful description
            tint = Color.Black
        )
    }
}
