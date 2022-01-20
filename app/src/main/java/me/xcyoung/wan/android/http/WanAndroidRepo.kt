package me.xcyoung.wan.android.http

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.xcyoung.wan.android.ArticleBannerVo
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.bean.NavListVo
import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.bean.TreeListVo
import me.xcyoung.wan.android.common.WanAndroidException

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
class WanAndroidRepo private constructor() {
    companion object {
        val instance by lazy { WanAndroidRepo() }
    }

    private val httpProvider = HttpProvider()

    fun articleTop(): Observable<WanResponse<List<ArticleVo.ArticleItemVo>>> {
        val service = httpProvider.createService(WanAndroidService::class.java)

        return service.articleTop()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun articleList(pageIndex: Int): Observable<WanResponse<ArticleVo>> {
        val service = httpProvider.createService(WanAndroidService::class.java)

        return service.articleList(pageIndex)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    suspend fun articleTopSuspend(): List<ArticleVo.ArticleItemVo> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return intercept(service.articleTopSuspend())
    }

    suspend fun articleListSuspend(pageIndex: Int, cid: Int? = null): ArticleVo {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return if (cid == null) {
            intercept(service.articleListSuspend(pageIndex))
        } else {
            intercept(service.articleListSuspend(pageIndex, cid))
        }
    }

    fun articleBanner(): Observable<WanResponse<List<ArticleBannerVo>>> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return service.articleBanner()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun treeList(): Observable<WanResponse<List<TreeListVo>>> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return service.treeList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun naviList(): Observable<WanResponse<List<NavListVo>>> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return service.naviList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    suspend fun wxarticleChapters(): List<OfficialListVo> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return intercept(service.wxarticleChapters())
    }

    suspend fun wxarticleList(pageIndex: Int, id: Int): ArticleVo {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return intercept(service.wxarticleList(id, pageIndex))
    }

    suspend fun projectTree(): List<OfficialListVo> {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return intercept(service.projectTree())
    }

    suspend fun projectList(pageIndex: Int, id: Int): ArticleVo {
        val service = httpProvider.createService(WanAndroidService::class.java)
        return intercept(service.projectList(pageIndex, id))
    }

    private fun <T> intercept(response: WanResponse<T>): T {
        if (response.errorCode == 0) {
            return response.data!!
        } else {
            throw WanAndroidException(response.errorCode, response.errorMsg)
        }
    }
}