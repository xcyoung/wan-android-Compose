package me.xcyoung.wan.android.module.article

import androidx.compose.material.ScaffoldState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.ArticleBannerVo
import me.xcyoung.wan.android.common.BaseViewModel
import me.xcyoung.wan.android.http.WanAndroidRepo
import me.xcyoung.wan.android.http.httpResult
import me.xcyoung.wan.android.http.subscribeWith

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
class ArticleViewModel(private val scaffoldState: ScaffoldState) : BaseViewModel() {
    val articlePagingData by lazy {
        Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                HomeArticlePagingSource()
            }
        ).flow.cachedIn(viewModelScope)
    }

    val articleBannerLiveData = MutableLiveData(emptyList<ArticleBannerVo>())
    val articleBannerIsRefreshingLiveData = MutableLiveData(false)

    fun articleBanner() {
        addDisposable(
            WanAndroidRepo.instance
                .articleBanner()
                .httpResult()
                .doOnSubscribe {
                    articleBannerIsRefreshingLiveData.value = true
                }
                .subscribeWith(onSuccess =
                {
                    articleBannerIsRefreshingLiveData.value = false
                    articleBannerLiveData.value = it
                }, onFailed = {
                    articleBannerIsRefreshingLiveData.value = false
                    viewModelScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(it.localizedMessage)
                    }
                })
        )
    }

    class Factory(private val scaffoldState: ScaffoldState) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ArticleViewModel(scaffoldState) as T
        }
    }
}