package net.cassiolandim.composepin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import net.cassiolandim.composepin.ui.theme.ComposePinTheme

class ImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePinTheme {
                ImageWithMarkers(
                    modifier = Modifier.fillMaxSize(),
                    imagePath = "/data/data/net.cassiolandim.composepin/files/bigimage.jpg",
                    imageWidth = 8192f,
                    imageHeight = 4159f,
                    markers = listOf()
                )
            }
        }
    }
}