package com.arekalov.yandexshmr.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaletteLight(modifier: Modifier = Modifier) {
    Column {
        Row {
            ColorBox(
                name = "Primary",
                containerColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary
            )
            ColorBox(
                name = "onPrimary",
                containerColor = MaterialTheme.colorScheme.onPrimary,
                textColor = MaterialTheme.colorScheme.primary
            )
        }
        Row {
            ColorBox(
                name = "Secondary",
                containerColor = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary
            )
            ColorBox(
                name = "onSecondary",
                containerColor = MaterialTheme.colorScheme.onSecondary,
                textColor = MaterialTheme.colorScheme.secondary
            )
        }

        Row {
            ColorBox(
                name = "Tertiary",
                containerColor = MaterialTheme.colorScheme.tertiary,
                textColor = MaterialTheme.colorScheme.onTertiary
            )
            ColorBox(
                name = "onTertiary",
                containerColor = MaterialTheme.colorScheme.onTertiary,
                textColor = MaterialTheme.colorScheme.tertiary
            )
        }
        Spacer(modifier = Modifier.padding(3.dp))
        Row {
            ColorBox(
                name = "Background",
                containerColor = MaterialTheme.colorScheme.background,
                textColor = MaterialTheme.colorScheme.onBackground
            )
            ColorBox(
                name = "onBackground",
                containerColor = MaterialTheme.colorScheme.onBackground,
                textColor = MaterialTheme.colorScheme.background
            )
        }
        Row {
            ColorBox(
                name = "Surface",
                containerColor = MaterialTheme.colorScheme.surface,
                textColor = MaterialTheme.colorScheme.onSurface
            )
            ColorBox(
                name = "onSurface",
                containerColor = MaterialTheme.colorScheme.onSurface,
                textColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
fun ColorBox(
    name: String,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(110.dp, 110.dp),
        color = containerColor
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = name,
                color = textColor
            )
            Text(
                text = "(r=${containerColor.red},\n g=${containerColor.green},\n b=${containerColor.blue},\n alfa=${containerColor.alpha})",
                color = textColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(5.dp)
            )
        }

    }
}

@Composable
fun TypographyDemonstration(modifier: Modifier = Modifier) {
    Surface {
        Column() {
            Text(
                text = "Headline medium (${MaterialTheme.typography.headlineMedium.fontSize})",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Title medium (${MaterialTheme.typography.titleMedium.fontSize})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Body large (${MaterialTheme.typography.bodyLarge.fontSize})",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Body medium (${MaterialTheme.typography.bodyMedium.fontSize})",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Body small (${MaterialTheme.typography.bodySmall.fontSize})",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light palette")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark palette")
@Composable
private fun PaletteLightPreview() {
    ToDoListTheme {
        PaletteLight()
    }
}

@Preview
@Composable
private fun TypographyPreview() {
    ToDoListTheme {
        TypographyDemonstration()
    }

}