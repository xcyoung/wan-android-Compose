package me.xcyoung.wan.android.module.category

import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
class TreeArticlePagingSource(private val cid: Int) : BaseArticlePagingSource() {
    override suspend fun getArticles(pageIndex: Int): List<ArticleVo.ArticleItemVo> {
        if (cid == -1) return emptyList()
        val articleList = WanAndroidRepo.instance.articleListSuspend(pageIndex, cid)
        return articleList.datas
    }
}