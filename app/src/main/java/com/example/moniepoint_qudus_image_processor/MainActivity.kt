package com.example.moniepoint_qudus_image_processor

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moniepoint_qudus_image_processor.presentation.screens.DownloadImageScreen
import com.example.moniepoint_qudus_image_processor.ui.theme.MoniepointqudusimageprocessorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoniepointqudusimageprocessorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()) { _ ->
                    DownloadImageScreen()
                }
            }
        }
    }
}