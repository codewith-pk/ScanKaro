package com.codewithpk.scankaro.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*

@Composable
fun checkAndRequestPermissions(
    context: Context,
    permissions: List<String>,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (List<String>) -> Unit
) {
    val permissionsToRequest = remember { mutableStateOf(permissions.filter {
        context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
    }) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        val deniedPermissions = result.filterValues { !it }.keys.toList()
        if (deniedPermissions.isEmpty()) {
            onPermissionGranted()
        } else {
            onPermissionDenied(deniedPermissions)
        }
    }

    LaunchedEffect(permissionsToRequest.value) {
        if (permissionsToRequest.value.isNotEmpty()) {
            launcher.launch(permissionsToRequest.value.toTypedArray())
        } else {
            onPermissionGranted()
        }
    }
}
