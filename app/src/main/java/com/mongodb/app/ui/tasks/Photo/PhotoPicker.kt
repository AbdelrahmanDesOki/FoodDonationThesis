package com.mongodb.app.ui.tasks.Photo

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.AddItemViewModel
import com.mongodb.app.ui.theme.Purple200


@SuppressLint("SuspiciousIndentation")
    @Composable
    fun SinglePhotoPicker(viewModel: AddItemViewModel) {
        var uri by remember {
            mutableStateOf<Uri?>(null)
        }

        val singlePhotoPicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                uri = it
            }
        )

        val context = LocalContext.current

            Column (modifier = Modifier.height(50.dp)){
                Row {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Purple200),
                        onClick = {
                            singlePhotoPicker.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }) {
                        Text("select an Image")
                    }

                    Button(

                        colors = ButtonDefaults.buttonColors(containerColor = Purple200),
                        onClick = {
                            uri?.let {
                                StorageUtil.uploadToStorage(
                                    uri = it,
                                    context = context,
                                    type = "image",
                                    task = Item(Firebase.auth.currentUser?.uid.toString()),
                                    viewModel
                                )

                            }

                        }) {
                        Text("Upload")
                    }
                }

                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.size(180.dp)
                )

            }




    }







//}






