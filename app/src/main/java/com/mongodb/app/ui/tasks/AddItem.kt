package com.mongodb.app.ui.tasks

import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mongodb.app.domain.PriorityLevel
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import com.mongodb.app.ComposeItemActivity
import com.mongodb.app.ComposeLoginActivity
import com.mongodb.app.MapsActivity
import com.mongodb.app.PhotoActivity
import com.mongodb.app.R
import com.mongodb.app.data.MockRepository
import com.mongodb.app.presentation.tasks.AddItemViewModel
import com.mongodb.app.ui.theme.MyApplicationTheme
import com.mongodb.app.ui.theme.Purple200


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemPrompt(viewModel: AddItemViewModel) {
    var navigateToActivity by remember { mutableStateOf(false) }
    var navigateToPhoto by remember { mutableStateOf(false) }
    var receivedMessage = ""

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
                    value = viewModel.taskSummary.value,
                    maxLines = 2,
                    onValueChange = {
                        viewModel.updateTaskSummary(it)
                    },
                    label = { Text(stringResource(R.string.item_summary)) }
                )
                //Details of food
                TextField (
                    colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.White),
                    value = viewModel.taskDescription.value,
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
                    // Launch the new activity using an Intent
                    var intent = Intent(LocalContext.current, PhotoActivity::class.java)

                    LocalContext.current.startActivity(intent)


                }


                Button(onClick = {
                    navigateToActivity = true
                                 },
                    modifier = Modifier
                        .padding(10.dp)
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
                    receivedMessage = intent.getStringExtra("EXTRA_MESSAGE").toString()
//                    startActivityForResult(MapsActivity, 101)
                    LocalContext.current.startActivity(intent)
//                    LocalContext.current.
//                    viewModel.Location_.value.equals()

                }

                val priorities = PriorityLevel.values()
                val intent = Intent(LocalContext.current,ComposeItemActivity::class.java)
//                val receivedMessage = intent.getStringExtra("EXTRA_MESSAGE")
                if (receivedMessage != null) {
                    Text(text =receivedMessage)
                    viewModel.Location_.value
//                    LocalContext.current.stopService(Intent(LocalContext.current, MapsActivity::class.java))
                }

                Text(text = "Expiry: ", Modifier.padding(4.dp))
                ExposedDropdownMenuBox(

                    modifier = Modifier.padding(16.dp),
                    expanded = viewModel.expanded.value,
                    onExpandedChange = { viewModel.open() },
                ) {
                    TextField(
                        readOnly = true,
                        value = viewModel.taskPriority.value.name,
                        onValueChange = {},
                        label = { Text(stringResource(R.string.item_priority)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.expanded.value) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = viewModel.expanded.value,
                        onDismissRequest = { viewModel.close() }
                    ) {
                        priorities.forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
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
            // If you're getting this app code by cloning the repository at
            // https://github.com/mongodb/template-app-kotlin-todo, 
            // it does not contain the data explorer link. Download the
            // app template from the Atlas UI to view a link to your data.
            var link = stringResource(R.string.dataExplorerLink)
            Button(
                colors = buttonColors(containerColor = Purple200),
                onClick = {
                    viewModel.addTask()
                    Log.v("TemplateApp","To see your data in Atlas, follow this link:"  + link)
                }
            ) {
                Text(stringResource(R.string.create))
            }
        },
        dismissButton = {
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
            AddItemPrompt(viewModel)
        }
    }
}
