package me.xcyoung.wan.android.common

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.xcyoung.wan.android.ArticleVo

abstract class BaseArticlePagingSource : PagingSource<Int, ArticleVo.ArticleItemVo>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleVo.ArticleItemVo>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleVo.ArticleItemVo> {
        val page = params.key ?: 0
        return try {
            val data = getArticles(page)
            LoadResult.Page(
                data,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun getArticles(pageIndex: Int): List<ArticleVo.ArticleItemVo>
}