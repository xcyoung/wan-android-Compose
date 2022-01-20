package me.xcyoung.wan.android.module.category

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.bean.NavListVo
import me.xcyoung.wan.android.bean.TreeListVo
import me.xcyoung.wan.android.common.BaseViewModel
import me.xcyoung.wan.android.http.WanAndroidRepo
import me.xcyoung.wan.android.http.httpResult
import me.xcyoung.wan.android.http.subscribeWith

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
class CategoryViewModel(private val scaffoldState: ScaffoldState) : BaseViewModel() {
    private val searchId = MutableStateFlow(-1)

    @ExperimentalCoroutinesApi
    val treePagingData: Flow<PagingData<ArticleVo.ArticleItemVo>> = searchId.flatMapLatest {
        Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                TreeArticlePagingSource(it)
            }
        ).flow
    }.cachedIn(viewModelScope)

    val treeListLiveData = MutableLiveData(emptyList<TreeListVo>())
    val treeListSelectedIdLiveData = MutableLiveData(-1)
    val navListLiveData = MutableLiveData(emptyList<NavListVo>())

    fun treeList() {
        addDisposable(
            WanAndroidRepo.instance
                .treeList()
                .httpResult()
                .subscribeWith(onSuccess = {
                    val selectedId = if (it.isNotEmpty() && it[0].children.isNotEmpty()) {
                        it[0].children[0].id
                    } else {
                        -1
                    }

                    searchId.value = selectedId
                    treeListSelectedIdLiveData.value = selectedId
                    treeListLiveData.value = it
                }, onFailed = {
                    viewModelScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it.localizedMessage)
                    }
                })
        )
    }

    fun treeListSelected(item: TreeListVo) {
        searchId.value = item.id
        treeListSelectedIdLiveData.value = item.id
    }

    fun navList() {
        addDisposable(
            WanAndroidRepo.instance
                .naviList()
                .httpResult()
                .subscribeWith(onSuccess = {
                    navListLiveData.value = it
                }, onFailed = {
                    viewModelScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it.localizedMessage)
                    }
                })
        )
    }

    class Factory(private val scaffoldState: ScaffoldState) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CategoryViewModel(scaffoldState) as T
        }
    }
}