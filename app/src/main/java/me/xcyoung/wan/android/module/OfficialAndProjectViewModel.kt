package me.xcyoung.wan.android.module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.common.BaseViewModel

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
abstract class OfficialAndProjectViewModel : BaseViewModel() {
    val classificationLiveData = MutableLiveData<List<OfficialListVo>>(emptyList())

    fun fetchClassification() {
        viewModelScope.launch {
            try {
                val chapters = fetchClassificationRequest()
                classificationLiveData.value = chapters
            } catch (e: Exception) {

            }
        }
    }

    fun createArticlePagingData(id: Int): Flow<PagingData<ArticleVo.ArticleItemVo>> {
        return Pager(
            PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                getPagingSource(id)
            }
        ).flow.cachedIn(viewModelScope)
    }

    protected abstract suspend fun fetchClassificationRequest(): List<OfficialListVo>
    protected abstract fun getPagingSource(id: Int): BaseArticlePagingSource
}