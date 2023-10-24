package com.mongodb.app.ui.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.appcompat.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.mongodb.app.MapsActivity
import com.mongodb.app.data.SyncRepository
import com.mongodb.app.presentation.tasks.AddItemViewModel
import java.io.IOException
import java.util.Locale


@Composable
fun MyMap(
    context: Context,
    latLng: LatLng,
    changeIcon: Boolean = false,
    lineType: LineType? = null,
    mapProperties: MapProperties = MapProperties(isMyLocationEnabled = true),
    onChangeMarkerIcon: () -> Unit,
    onChangeMapType: (mapType: MapType) -> Unit,
    onChangeLineType: (lineType: LineType?) -> Unit,
//    viewModel:AddItemViewModel
) {
    val latlangList = remember {
        mutableStateListOf(latLng)
    }
    var mapTypeMenuExpanded by remember { mutableStateOf(false) }
    var mapTypeMenuSelectedText by remember {
        mutableStateOf(
            MapType.NORMAL.name.capitaliseIt()
        )
    }
    var lineTypeMenuExpanded by remember { mutableStateOf(false) }
    var lineTypeMenuSelectedText by remember {
        mutableStateOf(
            lineType?.name?.capitaliseIt() ?: "Line Type"
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

//    val Loc =  Text(text = "Current Location is:" + getCurrentLocation(
//        this).toString())
    var markerLocation = LatLng(latlangList[0].latitude, latlangList[0].longitude)
    var locationAsString = getAddressFromLocation(context,markerLocation.latitude, markerLocation.longitude )
    val intent = Intent(LocalContext.current, MapsActivity::class.java)
    intent.putExtra("EXTRA_MESSAGE", locationAsString)
//    AddItemPrompt(viewModel = AddItemViewModel(SyncRepository), location =locationAsString)
//        "Latitude: ${markerLocation.latitude}, Longitude: ${markerLocation.longitude}"






    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = {
                if (lineType == null) {
                    latlangList.add(it)
                }
            }
        ) {


            latlangList.toList().forEach {
                Marker(

                    state = MarkerState(position = it),
                    title = "Location",
                    snippet = getAddressFromLocation(context,markerLocation.latitude, markerLocation.longitude ),
                    icon = if (changeIcon) {
                        bitmapDescriptor(context, R.drawable.abc_btn_radio_material)
                    } else null
                ){

                }
            }

            if (lineType == LineType.POLYLINE) {
                Polyline(points = latlangList, color = Color.Red)
            }
            if (lineType == LineType.POLYGON) {
                Polygon(
                    points = latlangList,
                    fillColor = Color.Green,
                    strokeColor = Color.Red,
                    strokeWidth = 2F
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {


            Button(onClick = onChangeMarkerIcon)
            {
                Text(text = if (changeIcon) "Default Marker" else "Custom Marker")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Row {
                Button(onClick = { mapTypeMenuExpanded = true }) {
                    Text(text = mapTypeMenuSelectedText)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown arrow",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                DropdownMenu(expanded = mapTypeMenuExpanded,
                    onDismissRequest = { mapTypeMenuExpanded = false }) {
                    MapType.values().forEach {
                        val mapType = it.name.capitaliseIt()
                        DropdownMenuItem(text = {
                            Text(text = mapType)
                        }, onClick = {
                            onChangeMapType(it)
                            mapTypeMenuSelectedText = mapType
                            mapTypeMenuExpanded = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            if (latlangList.size > 1) {
                Row {
                    Button(onClick = { lineTypeMenuExpanded = true }) {
                        Text(text = lineTypeMenuSelectedText)
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown arrow",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                    DropdownMenu(expanded = lineTypeMenuExpanded,
                        onDismissRequest = { lineTypeMenuExpanded = false }) {
                        val lineTypes = LineType.values().toMutableList()
                        if (latlangList.size < 3) {
                            lineTypes.remove(LineType.POLYGON)
                        }
                        lineTypes.forEach {
                            val lineType = it.name.capitaliseIt()
                            DropdownMenuItem(text = {
                                Text(text = lineType)
                            }, onClick = {
                                onChangeLineType(it)
                                lineTypeMenuSelectedText = lineType
                                lineTypeMenuExpanded = false
                            })
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = {
                        onChangeLineType(null)
                        latlangList.clear()
                        latlangList.add(latLng)
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Text(text = "Clear", color = Color.White)
                    }



                }
            }
            //need to save the current location
            Button(onClick = {
//                viewModel.Location_.value.equals(locationAsString)
                locationAsString = getAddressFromLocation(context,markerLocation.latitude, markerLocation.longitude )
                intent.putExtra("EXTRA_MESSAGE", locationAsString)
               //need to close the maps activity here
            },
                ) {

                Text(text = "Save My Location")
            }
        }

        if (lineType != null) {
            Text(
                text = when (lineType) {
                    LineType.POLYLINE -> "Total distance: ${
                        formattedValue(
                            calculateDistance(latlangList)
                        )
                    } km"

                    LineType.POLYGON -> "Total surface area: ${
                        formattedValue(
                            calculateSurfaceArea(latlangList)
                        )
                    } sq.meters"
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.White)
                    .padding(8.dp),
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}
//@Composable
//private fun getAddress(lat: Double, lon: Double) : String?  {
//    //return string that contain the exact location.
//    val geocoder = Geocoder(this, Locale.getDefault())
//    val address =  geocoder.getFromLocation(lat,lon, 1)
//
//
//    return  address?.get(0)?.getAddressLine(0).toString()
//}


fun getAddressFromLocation(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    var result = ""

    try {
        val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

        if (addresses != null && addresses.isNotEmpty()) {
            val address: Address = addresses[0]

            // Build the result string by concatenating address components
            val addressStringBuilder = StringBuilder()
            for (i in 0..address.maxAddressLineIndex) {
                addressStringBuilder.append(address.getAddressLine(i))
                if (i < address.maxAddressLineIndex) {
                    addressStringBuilder.append(", ")
                }
            }

            result = addressStringBuilder.toString()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return result
}