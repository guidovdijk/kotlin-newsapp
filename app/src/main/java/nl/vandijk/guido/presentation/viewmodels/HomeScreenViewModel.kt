package nl.vandijk.guido.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import nl.vandijk.guido.business.ArticlePager

class HomeScreenViewModel: ViewModel() {
    val articles = Pager(PagingConfig(pageSize = 20)){
        ArticlePager()
    }.flow.cachedIn(viewModelScope)
}