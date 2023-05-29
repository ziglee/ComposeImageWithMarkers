package net.cassiolandim.imagewithmarkers.library

import android.util.Log
import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GroundOverlay
import com.google.maps.android.compose.GroundOverlayPosition
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlin.math.atan2
import kotlin.math.sqrt

data class ImageMarker(
    @FloatRange(from = 0.0, to = 1.0) val x: Float,
    @FloatRange(from = 0.0, to = 1.0) val y: Float,
)

@Composable
fun ImageWithMarkers(
    imagePath: String,
    imageWidth: Float,
    imageHeight: Float,
    markers: List<ImageMarker>,
    modifier: Modifier = Modifier,
) {
    val zero = LatLng(0.0, 0.0)
    val bottomRight = calculateBottomRightCoordinatesOfImage(imageWidth, -imageHeight)
    val center = LatLng(
        bottomRight.latitude / 2,
        bottomRight.longitude / 2
    )
    val imgBounds = LatLngBounds.builder()
        .include(zero)
        .include(bottomRight)
        .build()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(center, 10f)
    }
    LaunchedEffect(imgBounds) {
        cameraPositionState.move(
            CameraUpdateFactory.newLatLngBounds(imgBounds, 30)
        )
    }
    GoogleMap(
        modifier = modifier,
        properties = MapProperties(
            mapType = MapType.NONE,
            latLngBoundsForCameraTarget = imgBounds,
            minZoomPreference = 11f,
            maxZoomPreference = 20f,
        ),
        uiSettings = MapUiSettings(
            compassEnabled = true,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            tiltGesturesEnabled = false,
            zoomControlsEnabled = true,
        ),
        cameraPositionState = cameraPositionState,
        onMapClick = {
            if (imgBounds.contains(it)) {
                Log.d("MAIN", convertToLatLngMarker(it, imgBounds).toString())
            }
        },
    ) {
        GroundOverlay(
            position = GroundOverlayPosition.create(
                location = zero,
                width = imageWidth,
                height = imageHeight,
            ),
            anchor = Offset.Zero,
            image = BitmapDescriptorFactory
                .fromPath(imagePath),
        )
        markers.forEach { marker ->
            val markerState = rememberMarkerState(position = convertMarkerToLatLng(marker, imgBounds))
            Marker(
                state = markerState,
                flat = true,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
    }
}

private fun calculateBottomRightCoordinatesOfImage(width: Float, height: Float): LatLng {
    val zero = LatLng(0.0, 0.0)
    val distance = calculateResultantVectorMagnitude(width, height)
    val heading = calculateResultantVectorAngle(width, height)
    return SphericalUtil.computeOffset(zero, distance.toDouble(), heading)
}

private fun calculateResultantVectorAngle(y: Float, x: Float): Double {
    return Math.toDegrees(atan2(y, x).toDouble())
}

private fun calculateResultantVectorMagnitude(x: Float, y: Float): Float {
    return sqrt(x * x + y * y)
}

private fun convertMarkerToLatLng(marker: ImageMarker, imgBounds: LatLngBounds): LatLng {
    val latitude = imgBounds.southwest.latitude
    val longitude = imgBounds.northeast.longitude
    return LatLng(
        latitude * marker.y,
        longitude * marker.x
    )
}

private fun convertToLatLngMarker(latLng: LatLng, imgBounds: LatLngBounds): ImageMarker {
    val latitude = imgBounds.southwest.latitude
    val longitude = imgBounds.northeast.longitude
    return ImageMarker(
        x = (latLng.longitude / longitude).toFloat(),
        y = (latLng.latitude / latitude).toFloat()
    )
}