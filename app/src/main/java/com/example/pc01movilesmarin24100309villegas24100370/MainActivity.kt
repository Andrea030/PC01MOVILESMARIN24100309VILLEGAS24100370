package com.example.pc01movilesmarin24100309villegas24100370

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pc01movilesmarin24100309villegas24100370.ui.theme.PC01MOVILESMARIN24100309VILLEGAS24100370Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01MOVILESMARIN24100309VILLEGAS24100370Theme {
                AppNavHost()
            }
        }
    }
}
