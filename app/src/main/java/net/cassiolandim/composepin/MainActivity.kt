package net.cassiolandim.composepin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import net.cassiolandim.composepin.ui.theme.ComposePinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePinTheme {
                Button(
                    onClick = {
                        Intent(this@MainActivity, ImageActivity::class.java).also {
                            startActivity(it)
                        }
                    }
                ) {
                    Text("abrir imagem")
                }
            }
        }
    }
}