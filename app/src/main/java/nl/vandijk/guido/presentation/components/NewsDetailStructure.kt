package nl.vandijk.guido.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.models.NewsCardModel

@Composable
fun NewsDetailStructure(news: NewsCardModel, isLoggedIn:Boolean, likedState:Boolean, updateArticleLikeState: () -> Unit, onOpenArticleClick: (String) -> Unit){
    Box(modifier = Modifier.height(200.dp)){
        AsyncImage(
            model = news.Image,
            contentDescription = null,
            placeholder = painterResource(R.drawable.ic_placeholder),
            error = painterResource(R.drawable.ic_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Row() {
        if(news.Url.isNotEmpty()){
            Text(
                text = stringResource(R.string.ui_articledetail_screen_uri_text),
                modifier = Modifier.clickable {
                    onOpenArticleClick(news.Url)
                }
            )
        }

        if(isLoggedIn){
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier
                    .padding(8.dp),
                onClick = {
                    updateArticleLikeState()
                }
            ) {
                if(likedState){
                    Icon(Icons.Filled.Favorite, stringResource(R.string.ui_icon_is_liked))
                } else {
                    Icon(Icons.Filled.FavoriteBorder, stringResource(R.string.ui_icon_is_not_liked))
                }
            }
        }
    }

    Text(text = news.Title, style = MaterialTheme.typography.headlineSmall)
    news.Summary?.let { s -> Text(text = s) }
}