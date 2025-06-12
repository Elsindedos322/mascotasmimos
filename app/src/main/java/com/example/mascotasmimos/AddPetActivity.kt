package com.example.mascotasmimos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.mascotasmimos.ui.theme.MascotasmimosTheme

class AddPetActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MascotasmimosTheme {
                AddPetScreen(onBack = { finish() })
            }
        }
    }
}