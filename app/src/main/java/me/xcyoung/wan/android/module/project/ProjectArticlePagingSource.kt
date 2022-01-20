package me.xcyoung.wan.android.module.project

import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
class ProjectArticlePagingSource(private val id: Int) : BaseArticlePagingSource() {
    override suspend fun getArticles(pageIndex: Int): List<ArticleVo.ArticleItemVo> {
        if (id == -1) return emptyList()
        val articleList = WanAndroidRepo.instance.projectList(pageIndex, id)
        return articleList.datas
    }
}