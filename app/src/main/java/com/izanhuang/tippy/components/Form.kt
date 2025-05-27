package com.izanhuang.tippy.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

const val labelTextWidth = 0.20f

@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        modifier = modifier,
        textAlign = TextAlign.Right
    )
}

@Composable
fun FormContainer(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    bottomContent: @Composable (() -> Unit)? = null,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LabelText(
                text = label, modifier = Modifier
                    .fillMaxWidth(labelTextWidth)
                    .padding(end = 16.dp)
            )
            content()
        }
        Row {
            if (bottomContent !== null) {
                bottomContent()
            }
        }
    }
}

@Composable
fun FormText(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
) {
    FormContainer(
        label = label,
        content = {
            Text(
                text = text,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier,
    )
}

@Composable
fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FormContainer(
        label = label,
        content = {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = modifier
    )
}

@Composable
fun FormTextFieldNumber(
    label: String,
    value: String,
    placeholder: @Composable() (() -> Unit)?,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FormContainer(
        label = label,
        content = {
            TextField(
                value = value,
                placeholder = placeholder,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        },
        modifier = modifier
    )
}

@Composable
fun FormSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    valueRange: IntRange,
    modifier: Modifier = Modifier,
    bottomContent: @Composable (() -> Unit)? = null,
) {
    FormContainer(
        label = label,
        content = {
            Slider(
                value = value.toFloat(),
                onValueChange = { newValue ->
                    onValueChange(newValue.toInt())
                },
                valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        bottomContent = bottomContent,
        modifier = modifier
    )
}