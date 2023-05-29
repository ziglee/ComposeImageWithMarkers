package net.cassiolandim.imagewithmarkers.library.model

import androidx.annotation.FloatRange
import androidx.compose.ui.geometry.Offset
import com.google.android.gms.maps.model.BitmapDescriptor

data class ImageMarker(
    val position: Position,
    val alpha: Float = 1.0f,
    val anchor: Offset = Offset(0.5f, 1.0f),
    val infoWindowAnchor: Offset = Offset(0.5f, 0.0f),
    val rotation: Float = 0.0f,
    val snippet: String? = null,
    val title: String? = null,
    val visible: Boolean = true,
    val zIndex: Float = 0.0f,
    val icon: BitmapDescriptor? = null,
) {
    data class Position(
        @FloatRange(from = 0.0, to = 1.0) val x: Float,
        @FloatRange(from = 0.0, to = 1.0) val y: Float,
    )
}