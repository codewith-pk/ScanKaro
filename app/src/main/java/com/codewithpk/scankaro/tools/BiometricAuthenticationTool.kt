package com.codewithpk.scankaro.ui.tools

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import com.codewithpk.scankaro.utils.BiometricPromptHelper

@Composable
fun BiometricAuthenticationTool(context: ComponentActivity) {
    val biometricHelper = remember {
        BiometricPromptHelper(
            context,
            onSuccess = { Toast.makeText(context, "Authentication succeeded", Toast.LENGTH_SHORT).show() },
            onFailure = { Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show() }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biometric Authentication") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (biometricHelper.isBiometricAvailable()) {
                        biometricHelper.showBiometricPrompt()
                    } else {
                        Toast.makeText(context, "Biometric authentication is not available", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Authenticate using Biometrics")
            }
        }
    }
}
