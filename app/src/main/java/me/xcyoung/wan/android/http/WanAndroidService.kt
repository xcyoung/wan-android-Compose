package me.xcyoung.wan.android.http

import io.reactivex.Observable
import me.xcyoung.wan.android.ArticleBannerVo
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.bean.NavListVo
import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.bean.TreeListVo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author ChorYeung
 * @since 2022/1/13
 */
interface WanAndroidService {
    @GET("article/top/json")
    fun articleTop(): Observable<WanResponse<List<ArticleVo.ArticleItemVo>>>

    @GET("article/list/{pageIndex}/json")
    fun articleList(@Path("pageIndex") pageIndex: Int): Observable<WanResponse<ArticleVo>>

    @GET("article/top/json")
    suspend fun articleTopSuspend(): WanResponse<List<ArticleVo.ArticleItemVo>>

    @GET("article/list/{pageIndex}/json")
    suspend fun articleListSuspend(@Path("pageIndex") pageIndex: Int): WanResponse<ArticleVo>

    @GET("article/list/{pageIndex}/json")
    suspend fun articleListSuspend(
        @Path("pageIndex") pageIndex: Int,
        @Query("cid") cid: Int
    ): WanResponse<ArticleVo>

    @GET("banner/json")
    fun articleBanner(): Observable<WanResponse<List<ArticleBannerVo>>>

    @GET("tree/json")
    fun treeList(): Observable<WanResponse<List<TreeListVo>>>

    @GET("navi/json")
    fun naviList(): Observable<WanResponse<List<NavListVo>>>

    @GET("wxarticle/chapters/json")
    suspend fun wxarticleChapters(): WanResponse<List<OfficialListVo>>

    @GET("wxarticle/list/{id}/{pageIndex}/json")
    suspend fun wxarticleList(
        @Path("id") id: Int,
        @Path("pageIndex") pageIndex: Int
    ): WanResponse<ArticleVo>

    @GET("project/tree/json")
    suspend fun projectTree(): WanResponse<List<OfficialListVo>>

    @GET("project/list/{pageIndex}/json")
    suspend fun projectList(
        @Path("pageIndex") pageIndex: Int,
        @Query("cid") id: Int
    ): WanResponse<ArticleVo>
}