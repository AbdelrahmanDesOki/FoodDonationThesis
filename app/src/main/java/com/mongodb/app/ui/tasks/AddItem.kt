package com.mongodb.app.ui.tasks

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

import com.mongodb.app.ComposeItemActivity
import com.mongodb.app.MapsActivity
import com.mongodb.app.PhotoActivity
import com.mongodb.app.R
import com.mongodb.app.data.MockRepository
import com.mongodb.app.data.SyncRepository
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.AddItemViewModel
import com.mongodb.app.ui.theme.MyApplicationTheme
import com.mongodb.app.ui.theme.Purple200



//@Composable
//fun navigation(){
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.MainScreen.route ){
//        composable(route = Screen.MainScreen.route){
//            MainScreen(navController = navController)
//        }
//
//        composable(route = Screen.SinglePhotoPicker.route,
//            arguments = navArgument("viewModel"){
//                type = NavType.ReferenceType
//            }
//
//        )
//    }
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemPrompt(viewModel: AddItemViewModel, task: Item) {
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

                    SinglePhotoPicker(viewModel = viewModel)

                }

                Spacer(modifier = Modifier.height(120.dp))
                Spacer(modifier = Modifier.height(60.dp))

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

//                    startActivityForResult(MapsActivity, 101)
                    LocalContext.current.startActivity(intent)

                    receivedMessage = task.Location
                    Log.d("checking task", receivedMessage)
                    val activity = MapsActivity()
                    activity.finish()
//                    LocalContext.current.stopService(intent)
//                    LocalContext.current.
//                    viewModel.Location_.value.equals()

                }

                val priorities = PriorityLevel.values()
//                val intent = Intent(LocalContext.current,ComposeItemActivity::class.java)
//                val receivedMessage = intent.getStringExtra("EXTRA_MESSAGE")
//                viewModel.updateLocation()
                if (receivedMessage != null) {
                    Text(text =receivedMessage)
                    viewModel.updateLocation(task.Location )
//                    LocalContext.current.stopService(Intent(LocalContext.current, MapsActivity::class.java))
                }

                Text(text = "Expiry: ", Modifier.padding(1.dp))
                ExposedDropdownMenuBox(

                    modifier = Modifier.padding(2.dp),
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
                        modifier = Modifier.height(3.dp),
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
            AddItemPrompt(viewModel, task = Item(Firebase.auth.currentUser?.uid.toString()))
        }
    }
}
