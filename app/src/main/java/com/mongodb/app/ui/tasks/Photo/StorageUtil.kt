package com.mongodb.app.ui.tasks.Photo

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.AddItemViewModel
import java.util.UUID


class StorageUtil {

    companion object {


        fun uploadToStorage(uri: Uri, context: Context, type: String, task: Item, viewModel: AddItemViewModel) {


            val storage = Firebase.storage
            // Create a storage reference from our app
            var storageRef = storage.reference
            val unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference
            //give the image unique name
            if (type == "image") {
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
                task.Image_URL = ("$unique_image_name.jpg").toString()
                viewModel.updatePhoto(task.Image_URL)
            }
            else {
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let {
                var uploadTask = spaceRef.putBytes(byteArray)
                // Handle unsuccessful uploads
                uploadTask.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "upload failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Handle successful uploads
                }.addOnSuccessListener { taskSnapshot ->
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    Toast.makeText(
                        context,
                        "upload succeed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
}