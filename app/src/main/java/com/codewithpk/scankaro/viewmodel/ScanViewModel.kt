package com.codewithpk.scankaro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScanViewModel : ViewModel() {
    private val _scanProgress = MutableStateFlow(0f)
    val scanProgress: StateFlow<Float> get() = _scanProgress

    private val _scanResults = MutableStateFlow(listOf<String>())
    val scanResults: StateFlow<List<String>> get() = _scanResults

    init {
        startScan()
    }

    private fun startScan() {
        viewModelScope.launch {
            // Simulate scanning process
            for (i in 1..100) {
                delay(50)
                _scanProgress.value = i.toFloat()
                _scanResults.value = _scanResults.value + "Scan result $i"
            }
        }
    }
}




