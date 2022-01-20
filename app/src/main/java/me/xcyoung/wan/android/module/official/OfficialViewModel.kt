package me.xcyoung.wan.android.module.official

import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo
import me.xcyoung.wan.android.module.OfficialAndProjectViewModel

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
class OfficialViewModel : OfficialAndProjectViewModel() {
    //    private val searchId = MutableStateFlow(-1)
//
//    @ExperimentalCoroutinesApi
//    val articlePagingData: Flow<PagingData<ArticleVo.ArticleItemVo>> = searchId.flatMapLatest {
//        Pager(
//            PagingConfig(
//                pageSize = 20,
//                prefetchDistance = 2,
//                initialLoadSize = 20
//            ),
//            pagingSourceFactory = {
//                OfficialArticlePagingSource(it)
//            }
//        ).flow
//    }.cachedIn(viewModelScope)
    override suspend fun fetchClassificationRequest(): List<OfficialListVo> {
        return WanAndroidRepo.instance.wxarticleChapters()
    }

    override fun getPagingSource(id: Int): BaseArticlePagingSource {
        return OfficialArticlePagingSource(id)
    }
}