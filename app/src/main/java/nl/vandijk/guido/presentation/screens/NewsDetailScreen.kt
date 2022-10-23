package nl.vandijk.guido.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.components.LoadingIndicator
import nl.vandijk.guido.presentation.components.Message
import nl.vandijk.guido.presentation.components.NewsDetailStructure
import nl.vandijk.guido.presentation.components.TopBar
import nl.vandijk.guido.presentation.viewmodels.NewsDetailViewModel
import nl.vandijk.guido.util.SessionManager
import java.io.IOException
import java.net.UnknownHostException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailPage(
    navController: NavController,
    detailId: Int,
    viewModel: NewsDetailViewModel = viewModel()
) {
    val uriHandler = LocalUriHandler.current

    val newsDetailModel by viewModel.articleState.collectAsState()
    val likedState = viewModel.likeState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val isLoggedIn = SessionManager.isLoggedIn()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetch(detailId)
    }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                isLoggedIn = isLoggedIn,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, stringResource(R.string.ui_icon_back))
                    }
                },
                refreshClick = { viewModel.refresh(detailId) }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                errorState?.let { m -> Message(uiMessageModel = m) }

                newsDetailModel?.onSuccess { news ->
                    NewsDetailStructure(
                        news,
                        isLoggedIn,
                        likedState.value,
                        updateArticleLikeState = {
                            viewModel.updateArticleLikeState(
                                news.Id,
                                news.IsLiked
                            )
                        },
                        onOpenArticleClick = { uriHandler.openUri(news.Url) }
                    )
                } ?: run {
                    if (errorState == null) {
                        LoadingIndicator()
                    }
                }
            }
        }
    )
}