package me.xcyoung.wan.android.module.official

import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
class OfficialArticlePagingSource(private val id: Int) : BaseArticlePagingSource() {
    override suspend fun getArticles(pageIndex: Int): List<ArticleVo.ArticleItemVo> {
        if (id == -1) return emptyList()
        val articleList = WanAndroidRepo.instance.wxarticleList(pageIndex, id)
        return articleList.datas
    }
}