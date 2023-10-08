package com.mongodb.app.ui.tasks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mongodb.app.R
import com.mongodb.app.data.MockRepository
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.ItemContextualMenuViewModel
import com.mongodb.app.presentation.tasks.TaskViewModel
import com.mongodb.app.ui.theme.Blue
import com.mongodb.app.ui.theme.MyApplicationTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import com.mongodb.app.ui.tasks.ChatPage
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import com.mongodb.app.ui.theme.Purple200
import java.time.format.TextStyle


@Composable
fun TaskItem(
    taskViewModel: TaskViewModel,
    itemContextualMenuViewModel: ItemContextualMenuViewModel,
    task: Item,

) {
    var chatTabOpened by remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 20.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow), label = ""
    )

    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(80.dp)
        ) {

            ChatButton(onClick = { chatTabOpened = true })


            Column {
                Text(
                    text = (task.summary),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp

                    )
                // Ownership text visible only if task is mine
//                if (taskViewModel.isTaskMine(task)) {
//                    Text(
//                        text = stringResource(R.string.mine),
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = { expanded.value = !expanded.value },
                    colors = ButtonDefaults.buttonColors(containerColor = Purple200),
                    modifier = Modifier.padding(3.dp)

                ) {

                    Text(if (expanded.value) "Show Less" else "Show More")

                }


                ItemContextualMenu(itemContextualMenuViewModel, task)

            }
        }
            if(expanded.value){

                Column(modifier = Modifier.padding(
                    bottom = extraPadding.coerceAtLeast(5.dp)
                )) {
                    Text(text = task.description)
                }
            }

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
    onClick: () -> Unit,

) {
    IconButton(
        onClick = { onClick },
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
