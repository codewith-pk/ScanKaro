package com.codewithpk.scankaro.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.codewithpk.scankaro.ui.components.PermissionDialog
import com.codewithpk.scankaro.ui.tools.BiometricAuthenticationTool
import com.codewithpk.scankaro.utils.ToolPermissions
import com.codewithpk.scankaro.utils.BiometricPromptHelper
import androidx.activity.ComponentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolDetailScreen(navController: NavController, toolName: String) {
    val context = LocalContext.current as ComponentActivity
    var showPermissionDialog by remember { mutableStateOf(false) }
    var permissionsToRequest by remember { mutableStateOf(listOf<String>()) }
    var permissionGranted by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val deniedPermissions = permissions.filter { !it.value }.keys
        if (deniedPermissions.isEmpty()) {
            permissionGranted = true
        } else {
            showPermissionDialog = true
            permissionsToRequest = deniedPermissions.toList()
        }
    }

    fun checkPermissions() {
        val permissions = when (toolName) {
            "Biometric Authentication" -> ToolPermissions.BIOMETRIC_AUTHENTICATION
            // Add other tools here with their permissions
            else -> listOf()
        }
        val neededPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isEmpty()) {
            permissionGranted = true
        } else {
            launcher.launch(neededPermissions.toTypedArray())
        }
    }

    if (showPermissionDialog) {
        PermissionDialog(
            title = "Permission Required",
            message = "This tool requires the following permissions: ${permissionsToRequest.joinToString(", ")}. Please grant the permissions to proceed.",
            onDismiss = { showPermissionDialog = false },
            onConfirm = { checkPermissions() }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(toolName) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Button(
                onClick = { checkPermissions() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Test $toolName")
            }

            Text(
                text = "Guide and Explanation",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "This tool allows you to authenticate using biometric features such as fingerprints. It enhances security by ensuring that only you can access certain functionalities.",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pros and Cons",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Pros:\n- High security\n- Convenient\n- Fast authentication\n\nCons:\n- Requires biometric hardware\n- May not work well with injuries or worn-out fingerprints",
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Display the tool if permissions are granted
            if (permissionGranted) {
                when (toolName) {
                    "Biometric Authentication" -> BiometricAuthenticationTool(context)
                    // Add more tools here
                }
            }
        }
    }
}
