package com.example.moniepoint_qudus_image_processor.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moniepoint_qudus_image_processor.presentation.components.AppScaffold
import com.example.moniepoint_qudus_image_processor.presentation.viewmodels.DownloadImageViewModel
import com.example.moniepoint_qudus_image_processor.ui.theme.MoniepointqudusimageprocessorTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DownloadImageScreen(viewModel: DownloadImageViewModel = hiltViewModel()) {
    var formField by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    AppScaffold(title = "Download Screen", modifier = Modifier.fillMaxWidth()) { ->
        val uiState = viewModel.uiState.value

        Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = formField,
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },
                    onValueChange = {
                        formField = it
                    },
                    label = { Text(text = "Image URL") },
                    placeholder = { Text(text = "Enter Url") },
                )
                Box(
                    modifier = Modifier.height(
                        20.dp
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp
                        ),
                    onClick = {
                            viewModel.downloadImage(formField.text)
                    },
                ) {
                    Text("Download Image")
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(viewModel: DownloadImageViewModel = hiltViewModel()) {
    MoniepointqudusimageprocessorTheme {
        DownloadImageScreen()
    }
}