package nl.vandijk.guido.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import nl.vandijk.guido.R
import nl.vandijk.guido.presentation.components.*
import nl.vandijk.guido.presentation.models.UiMessageModel
import nl.vandijk.guido.presentation.viewmodels.HomeScreenViewModel
import nl.vandijk.guido.util.SessionManager

@Composable
private fun ArticleState(state: LoadState, modifier: Modifier = Modifier) {
    when (state) {
        is LoadState.Loading -> {
            LoadingIndicator()
        }
        is LoadState.Error -> Message(UiMessageModel(R.string.api_error_general, false))
        is LoadState.NotLoading -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    loginClick: () -> Unit,
    onDetailClick: (Int) -> Unit,
    pageNavigation: (String) -> Unit,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val articlePaging = viewModel.articles.collectAsLazyPagingItems()
    val state = articlePaging.loadState
    val isLoggedIn: Boolean = SessionManager.isLoggedIn()

    Scaffold(
        topBar = {
            TopBar(
                navController,
                isLoggedIn = isLoggedIn,
                loginClick = loginClick,
                refreshClick = { articlePaging.refresh() }
            )
        },
        content = {
            Column(
                Modifier
                    .padding(it)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    item { ArticleState(state.prepend) }

                    items(articlePaging) { item ->
                        if (item != null) {
                            NewsCard(item, onDetailClicked = onDetailClick)
                        }
                    }

                    item { ArticleState(state.append) }
                }

                ArticleState(state.refresh)
            }
        },
        bottomBar = {
            BottomBar(navController, isLoggedIn, pageNavigation)
        }
    )
}