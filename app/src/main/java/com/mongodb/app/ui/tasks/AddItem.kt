package com.mongodb.app.ui.tasks

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mongodb.app.domain.PriorityLevel
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.mongodb.app.MapsActivity
import com.mongodb.app.PreferencesManager
import com.mongodb.app.R
import com.mongodb.app.data.MockRepository
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.AddItemViewModel
import com.mongodb.app.ui.tasks.Photo.SinglePhotoPicker
import com.mongodb.app.ui.theme.MyApplicationTheme
import com.mongodb.app.ui.theme.Purple200




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemPrompt(viewModel: AddItemViewModel, task: Item, context: Context) {
    var navigateToActivity by remember { mutableStateOf(false) }
    var navigateToPhoto by remember { mutableStateOf(false) }
    var receivedMessage = ""
    val preferencesManager = remember { PreferencesManager(context) }
    val data = remember { mutableStateOf(preferencesManager.getData("myKey", "")) }

    AlertDialog(

        containerColor = Color.White,
        onDismissRequest = {
            viewModel.closeAddTaskDialog()
        },
        title = { Text(stringResource(R.string.add_item)) },
        text = {
            Column {
                Text(stringResource(R.string.enter_item_name))
                //title of food
                TextField(
                    colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.White),
                    value = viewModel.ItemSummary.value,
                    maxLines = 2,
                    onValueChange = {
                        viewModel.updateTaskSummary(it)
                    },
                    label = { Text(stringResource(R.string.item_summary)) }
                )
                //Details of food
                TextField (
                    colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.White),
                    value = viewModel.ItemDescription.value,
                    maxLines = 6,
                    onValueChange = {
                        viewModel.updateTaskDescription(it)
                    },
                    label = { Text(stringResource(R.string.item_Details)) }
                )

                Button(onClick = { navigateToPhoto = true },
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    colors = buttonColors(containerColor = Purple200)
                ) {
                    Text(text = "Add Photo")
                }
                if (navigateToPhoto) {
                    // Use the BackHandler to clear the flag when navigating back
                    BackHandler {
                        navigateToPhoto = false
                    }

                    SinglePhotoPicker(viewModel = viewModel)

                }


                Button(onClick = {
                    navigateToActivity = true
                },
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    colors = buttonColors(containerColor = Purple200)
                ) {

                    Text(text = "Add your Location")
                }
                if (navigateToActivity) {
                    // Use the BackHandler to clear the flag when navigating back
                    BackHandler {
                        navigateToActivity = false
                    }
                    // Launch the new activity using an Intent
                    var intent = Intent(LocalContext.current, MapsActivity::class.java)
                    LocalContext.current.startActivity(intent)

//                    val activity = MapsActivity()
//                    activity.finish()


                }
//                task.Location = saveLocation(task.Location)

                receivedMessage = data.value.toString()
                   task.Location = receivedMessage
                Log.d("checking task", receivedMessage)
                Log.d("checking loc", task.Location)
//                LocalContext.current.


                val priorities = PriorityLevel.values()

                if (receivedMessage != null) {
                    Text(text = "Location: $receivedMessage")
                    viewModel.updateLocation(receivedMessage)

                }
//                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Expiry: ", Modifier.padding(1.dp))
                Spacer(modifier = Modifier.height(15.dp))
                ExposedDropdownMenuBox(

                    modifier = Modifier.padding(2.dp),
                    expanded = viewModel.expanded.value,
                    onExpandedChange = { viewModel.open() },
                ) {
                    TextField(
                        readOnly = true,

                        value = viewModel.ItemPriority.value.name,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.item_priority)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.expanded.value) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                            .safeContentPadding()
                    )
//                    Spacer(modifier = Modifier.height(10.dp))
                    ExposedDropdownMenu(
                        modifier = Modifier.height(20.dp),
                        expanded = viewModel.expanded.value,

                        onDismissRequest = { viewModel.close() }
                    ) {
                        priorities.forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    viewModel.updateTaskPriority(it)
                                    viewModel.close()
                                }
                            )
                        }
                    }
                }
            }
        },

        confirmButton = {
//            Spacer(modifier = Modifier.height(20.dp))
            var link = stringResource(R.string.dataExplorerLink)
            Button(
                colors = buttonColors(containerColor = Purple200),
                onClick = {
                    viewModel.addTask()

                }
            ) {
                Text(stringResource(R.string.create))
            }
        },
        dismissButton = {
//            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = buttonColors(containerColor = Purple200),
                onClick = {
                    viewModel.closeAddTaskDialog()
                }
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}





@Preview(showBackground = true)
@Composable
fun AddItemPreview() {
    MyApplicationTheme {
        MyApplicationTheme {
            val repository = MockRepository()
            val viewModel = AddItemViewModel(repository)
            val context = LocalContext.current
            AddItemPrompt(viewModel, task = Item(Firebase.auth.currentUser?.uid.toString()), context)
        }
    }
}
