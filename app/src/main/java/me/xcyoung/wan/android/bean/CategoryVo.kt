package me.xcyoung.wan.android.bean

import me.xcyoung.wan.android.ArticleVo

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
data class TreeListVo(
    val children: List<TreeListVo> = emptyList(),
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    var selected: Boolean = false
)

data class NavListVo(
    val articles: List<ArticleVo.ArticleItemVo>,
    val cid: Int,
    val name: String,
)