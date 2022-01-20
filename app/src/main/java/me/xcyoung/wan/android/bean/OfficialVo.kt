package me.xcyoung.wan.android.bean

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
data class OfficialListVo(
    val courseId: Int, val id: Int,
    val name: String,
    val order: Int, val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)