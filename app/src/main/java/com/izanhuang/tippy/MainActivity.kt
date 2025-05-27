package com.izanhuang.tippy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.izanhuang.tippy.ui.theme.TippyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TippyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Tippy(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Tippy(
    modifier: Modifier = Modifier
) {
    TipCalculator(modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TippyTheme {
        Tippy()
    }
}