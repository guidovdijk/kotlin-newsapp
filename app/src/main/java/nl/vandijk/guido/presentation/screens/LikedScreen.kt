package nl.vandijk.guido.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import nl.vandijk.guido.presentation.components.*
import nl.vandijk.guido.presentation.viewmodels.LikedScreenViewModel
import nl.vandijk.guido.util.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedScreen(
    navController: NavController,
    loginClick: () -> Unit,
    onDetailClick: (Int) -> Unit,
    pageNavigation: (String) -> Unit,
    viewModel: LikedScreenViewModel = viewModel()
) {
    val articleList by viewModel.articleListState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()
    val isLoggedIn: Boolean = SessionManager.isLoggedIn()

    Scaffold(
        topBar = {
            TopBar(
                navController,
                isLoggedIn = isLoggedIn,
                loginClick = loginClick,
                refreshClick = { viewModel.refresh() }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(8.dp)
            ) {
                errorState?.let { m -> Message(uiMessageModel = m) }

                LazyColumn() {
                    articleList?.let { list ->
                        list.onSuccess { list ->
                            items(list) { item ->
                                NewsCard(item, onDetailClicked = onDetailClick)
                            }
                        }
                    } ?: run {
                        if (errorState == null) {
                            item { LoadingIndicator() }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(navController, isLoggedIn, pageNavigation)
        }
    )
}