package nl.vandijk.guido.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.models.NewsCardModel

@Composable
fun NewsCard(newsCardModel: NewsCardModel, onDetailClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable {
                onDetailClicked(newsCardModel.Id)
            }
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            AsyncImage(
                model = newsCardModel.Image,
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_placeholder)
            )
            Spacer(Modifier.width(8.dp))
            Text(text = newsCardModel.Title, modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(8.dp)
                ) {
                if(newsCardModel.IsLiked){
                    Icon(Icons.Filled.Favorite, stringResource(R.string.ui_icon_is_liked))
                }
            }
        }
    }
}