package com.mongodb.app.ui.tasks

sealed class Screen (val route :String){

    object MainScreen: Screen("main_screen")
    object MapScreen:Screen("map_screen")
//    object PhotoScreen:Screen("photo_screen")
    object SinglePhotoPicker:Screen("photo_screen")
}
