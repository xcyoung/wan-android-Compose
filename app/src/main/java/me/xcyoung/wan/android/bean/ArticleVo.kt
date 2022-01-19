package me.xcyoung.wan.android

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
data class ArticleVo(
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val curPage: Int = 0,
    val datas: List<ArticleItemVo>,
) {
    data class ArticleItemVo(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long?,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<ArticleTagVo>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ) {
        data class ArticleTagVo(
            val name: String,
            val url: String
        )
    }
}

data class ArticleBannerVo(
    val imagePath: String,
    val url: String
)