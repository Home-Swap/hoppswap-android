package com.hoppswap.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Heading(@StringRes text: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier
    )
}

@Composable
fun HeadingSmall(@StringRes text: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun EmptyLabel(@StringRes text: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(text),
        color = MaterialTheme.colorScheme.secondary,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun FormError(@StringRes text: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(text),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}