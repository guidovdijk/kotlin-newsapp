package nl.vandijk.guido.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import nl.vandijk.guido.presentation.models.UiMessageModel

@Composable
fun Message(uiMessageModel: UiMessageModel){
    val color = if(uiMessageModel.Success){
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.error
    }
    Text(
        text = stringResource(uiMessageModel.MessageId),
        color = color
    )
}