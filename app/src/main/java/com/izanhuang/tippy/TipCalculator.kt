package com.izanhuang.tippy

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.izanhuang.tippy.components.FormContainer
import com.izanhuang.tippy.components.FormSlider
import com.izanhuang.tippy.components.FormText
import com.izanhuang.tippy.components.FormTextFieldNumber

@SuppressLint("DefaultLocale")
@Composable
fun TipCalculator(modifier: Modifier = Modifier) {
    var baseInput by remember { mutableStateOf("") }
    var tipPercent by remember { mutableIntStateOf(15) }
    var tipAmount by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }

    fun calculateTipAndTotal(base: String, percent: Int): Pair<String, String> {
        val baseValue = base.toFloatOrNull() ?: 0f
        val tip = baseValue * (percent.toFloat() / 100)
        val total = baseValue + tip
        return String.format("%.2f", tip) to String.format("%.2f", total)
    }

    val formSeparationPadding = 32.dp

    Column(
        modifier = modifier.padding(top = 32.dp, bottom = 32.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = "Tip Calculator",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth().padding(bottom = formSeparationPadding),
            textAlign = TextAlign.Center
        )

        FormTextFieldNumber(
            label = "Base",
            value = baseInput,
            placeholder = {
                Text(text = "Bill Amount")
            },
            onValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    baseInput = ""
                    tipAmount = ""
                    totalAmount = ""
                } else if (newValue.matches(Regex("^\\d*\\.?\\d{0,2}\$"))) {
                    baseInput = newValue
                    calculateTipAndTotal(
                        base = baseInput,
                        percent = tipPercent
                    ).let { (tip, total) ->
                        tipAmount = tip
                        totalAmount = total
                    }
                }
            },
            modifier = Modifier.padding(bottom = formSeparationPadding)
        )

        FormSlider(
            label = "$tipPercent%",
            value = tipPercent,
            onValueChange = { newValue ->
                tipPercent = newValue
                val baseValue = baseInput.toFloatOrNull()
                if (baseValue != null) {
                    calculateTipAndTotal(
                        base = baseInput,
                        percent = tipPercent
                    ).let { (tip, amount) ->
                        tipAmount = tip
                        totalAmount = amount
                    }
                }
            },
            valueRange = 0..30,
            bottomContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = when (tipPercent) {
                            in 0..9 ->
                                "Tip percentage is low, consider increasing it."

                            in 10..20 ->
                                "Tip percentage is reasonable."

                            else -> "Tip percentage is high, you are very generous!"
                        },
                        color = when (tipPercent) {
                            in 0..9 ->
                                MaterialTheme.colorScheme.error

                            in 10..20 ->
                                MaterialTheme.colorScheme.tertiary

                            else -> MaterialTheme.colorScheme.primary
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = 8.dp)
                    )
                }
            },
            modifier = Modifier.padding(bottom = formSeparationPadding)
        )

        FormText(
            label = "Tip:",
            text = tipAmount,
            modifier = Modifier.padding(bottom = formSeparationPadding)
        )

        FormText(
            label = "Total:",
            text = totalAmount,
        )
    }
}