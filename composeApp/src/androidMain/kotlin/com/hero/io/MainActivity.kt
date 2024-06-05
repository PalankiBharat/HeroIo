package com.hero.io

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hero.domain.model.Superhero
import ui.home.SuperheroCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}





