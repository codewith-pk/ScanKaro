// ToolViewModel.kt
package com.codewithpk.scankaro.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ToolViewModel : ViewModel() {
    var securityScore by mutableStateOf(0)
        private set

    fun startDeviceScan() {
        viewModelScope.launch {
            // Simulate scanning process
            delay(3000)
            securityScore = (0..100).random() // Random security score for demo purposes
        }
    }

}
