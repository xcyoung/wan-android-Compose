package me.xcyoung.wan.android.module.article

import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo

class HomeArticlePagingSource : BaseArticlePagingSource() {
    override suspend fun getArticles(pageIndex: Int): List<ArticleVo.ArticleItemVo> {
        return if (pageIndex == 0) {
            val articleTop = WanAndroidRepo.instance.articleTopSuspend()
            val articleList = WanAndroidRepo.instance.articleListSuspend(pageIndex)
            val data = articleTop + articleList.datas
            data
        } else {
            val articleList = WanAndroidRepo.instance.articleListSuspend(pageIndex)
            val data = articleList.datas
            data
        }
    }
}