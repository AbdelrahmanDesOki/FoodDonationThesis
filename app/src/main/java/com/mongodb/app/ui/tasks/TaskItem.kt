package com.mongodb.app.ui.tasks

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.mongodb.app.ChatActivity
import com.mongodb.app.ComposeItemActivity
import com.mongodb.app.MapsActivity
import com.mongodb.app.data.MockRepository
import com.mongodb.app.domain.Item
import com.mongodb.app.presentation.tasks.ItemContextualMenuViewModel
import com.mongodb.app.presentation.tasks.TaskViewModel
import com.mongodb.app.ui.theme.Blue
import com.mongodb.app.ui.theme.MyApplicationTheme
import com.mongodb.app.ui.theme.Purple200


@Composable
fun TaskItem(
    taskViewModel: TaskViewModel,
    itemContextualMenuViewModel: ItemContextualMenuViewModel,
    task: Item,
    homeViewModel: HomeViewModel = viewModel()

) {
    var photoloaded by remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 20.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow), label = ""
    )
    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val storageReference = FirebaseStorage.getInstance().getReference("images/$uid.jpg")
//    val storageReference = Firebase.storage.reference.child("images/$uid.jpg")
    var imageUrl = ""





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


            ChatButton(onClick = {



            })



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

                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        // Use the URI to display or process the image
                         imageUrl = uri.toString()
                         photoloaded = true

                        // Load and display the image using Picasso, Glide, or another image loading library
                    }.addOnFailureListener { exception ->
                        // Handle any errors that occurred during the download


                    }
                    Spacer(modifier = Modifier.height(10.dp))

                     if(photoloaded){
                         //                    AsyncImage(model = imageUrl, contentDescription = null, modifier = Modifier.size(248.dp))

                         Image(
                             painter = rememberAsyncImagePainter(imageUrl),
                             contentDescription = null,
                             modifier = Modifier.size(150.dp) // Modify the size as needed
                         )
                     }



                }
            }

    }

}

//@Composable
//fun ConditionalNavigation() {
//    val currentUser = FirebaseAuth.getInstance().currentUser
//
//    if (currentUser != null) {
//        HomeView()
//    } else {
//        // Display a login screen or navigate to it.
//        // You can use the Navigation component for navigation.
//        Text("Please log in")
//    }
//}

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
    var navigateToActivity by remember { mutableStateOf(false) }
    IconButton(
        onClick = { navigateToActivity =true },
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

    if (navigateToActivity) {
        // Use the BackHandler to clear the flag when navigating back
        BackHandler {
            navigateToActivity = false
        }
        // Launch the new activity using an Intent
        var intent = Intent(LocalContext.current, ChatActivity::class.java)

        LocalContext.current.startActivity(intent)
    }
}
