package net.cassiolandim.composepin

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun angle_0_isCorrect() {
        assertEquals(0.0, calculateResultantVectorAngle(0.0f, 1.0f), 0.1)
    }

    @Test
    fun angle_45_isCorrect() {
        assertEquals(45.0, calculateResultantVectorAngle(1.0f, 1.0f), 0.1)
    }

    @Test
    fun angle_90_isCorrect() {
        assertEquals(90.0, calculateResultantVectorAngle(1.0f, 0.0f), 0.1)
    }

    @Test
    fun angle_135_isCorrect() {
        assertEquals(135.0, calculateResultantVectorAngle(1.0f, -1.0f), 0.1)
    }

    @Test
    fun angle_180_isCorrect() {
        assertEquals(180.0, calculateResultantVectorAngle(0.0f, -1.0f), 0.1)
    }

    @Test
    fun magnitude_isCorrect() {
        assertEquals(1.4142135f, calculateResultantVectorMagnitude(1.0f, 1.0f), 0.0001f)
    }
}

fun calculateResultantVectorAngle(y: Float, x: Float): Double {
    return Math.toDegrees(atan2(y, x).toDouble())
}

fun calculateResultantVectorMagnitude(x: Float, y: Float): Float {
    return sqrt(x * x + y * y)
}

private fun calculateBottomRightCoordinatesOfImage(width: Float, height: Float): LatLng {
    val zero = LatLng(0.0, 0.0)
    val distance = calculateResultantVectorMagnitude(width, height)
    val heading = calculateResultantVectorAngle(width, height)
    return SphericalUtil.computeOffset(zero, distance.toDouble(), heading)
}