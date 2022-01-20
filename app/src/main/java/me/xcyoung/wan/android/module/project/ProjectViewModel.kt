package me.xcyoung.wan.android.module.project

import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.common.BaseArticlePagingSource
import me.xcyoung.wan.android.http.WanAndroidRepo
import me.xcyoung.wan.android.module.OfficialAndProjectViewModel

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
class ProjectViewModel : OfficialAndProjectViewModel() {
    override suspend fun fetchClassificationRequest(): List<OfficialListVo> {
        return WanAndroidRepo.instance.projectTree()
    }

    override fun getPagingSource(id: Int): BaseArticlePagingSource {
        return ProjectArticlePagingSource(id = id)
    }

}