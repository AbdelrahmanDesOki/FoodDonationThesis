package com.mongodb.app.ui.tasks

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import coil.compose.AsyncImage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.UUID


@Composable
fun SinglePhotoPicker(){
    var uri by remember{
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            uri = it
        }
    )

    val context = LocalContext.current


    Column{
        Button(onClick = {
            uploadImage()
//            singlePhotoPicker.launch(
//                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//            )

//            try {
//                startActivityForResult(
//                    Intent(MediaStore.ACTION_IMAGE_CAPTURE),
//                    CAMERA_REQUEST_CODE
//                )
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        }){
            Text("Pick an Image")
        }


        AsyncImage(model = uri, contentDescription = null, modifier = Modifier.size(248.dp))

        Button(onClick = {
            uri?.let{
                StorageUtil.uploadToStorage(uri=it, context=context, type="image")
            }

        }){
            Text("Upload")
        }

    }
}

@Throws(Exception::class)
private fun uploadImage() {
    var uploadBitmap: Bitmap? = null
    var context: Context? = null
    //converting image to bytes to be able to upload it.
    val bias = ByteArrayOutputStream()
    uploadBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bias)
    val imageInBytes = bias.toByteArray()
    val storageRef = Firebase.storage.reference
    val newImage = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
    val newImagesRef = storageRef.child("Images/$newImage")
    newImagesRef.putBytes(imageInBytes)
        .addOnFailureListener { exception ->
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                .show()
        }.addOnSuccessListener { taskSnapshot ->
            newImagesRef.downloadUrl.addOnCompleteListener (
                object : OnCompleteListener<Uri> {
                    override fun onComplete(p0: Task<Uri>) {
//                    sendItem(p0.result.toString())
                    }
                })
        }
}
