package me.xcyoung.wan.android.module.article

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.module.browser.BrowserActivity
import me.xcyoung.wan.android.ui.ArticleList

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
@ExperimentalPagerApi
@Composable
fun ArticlePage(viewModel: ArticleViewModel) {
    val lazyPagingItems =
        viewModel.articlePagingData.collectAsLazyPagingItems()
    val bannerVos by viewModel.articleBannerLiveData.observeAsState()
    val isRefreshing by viewModel.articleBannerIsRefreshingLiveData.observeAsState()
    val context: Context = LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "首页", style = MaterialTheme.typography.h6)
            },
            actions = {
                IconButton(onClick = {
                    
                }) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            },
            elevation = 0.dp
        )
    }, content = {
        ArticleList(
            isRefreshing = isRefreshing ?: false,
            lazyPagingItems = lazyPagingItems,
            onRefresh = {
                viewModel.articleBanner()
            },
            content = {
                if (bannerVos?.isNotEmpty() == true) {
                    item {
                        HorizontalPager(
                            count = bannerVos?.count() ?: 0,
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(120.dp)
                        ) { pagerScope ->
                            if (bannerVos.isNullOrEmpty()) return@HorizontalPager
                            val vo = bannerVos!![pagerScope]
                            Image(
                                painter = rememberImagePainter(vo.imagePath),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = RoundedCornerShape(16.dp))
                                    .clickable {
                                        BrowserActivity.start(context, vo.url)
                                    },
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }

                    }
                }

                itemsIndexed(lazyPagingItems) { index, item ->
                    item ?: return@itemsIndexed
                    ArticleItem(item = item)
                    if (index < lazyPagingItems.itemCount - 1) {
                        Divider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colors.primaryVariant
                        )
                    }
                }

            }
        )
    })
}

@Composable
fun ArticleItem(item: ArticleVo.ArticleItemVo) {
    val context: Context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .clickable {
                BrowserActivity.start(context, item.link)
            }
    ) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)) {
            if (item.fresh) {
                Text(
                    text = "新",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondary
                )
            }
            Text(
                text = if (item.author.isEmpty()) item.shareUser else "${item.author}(Author)",
                modifier = if (item.fresh) Modifier.padding(start = 8.dp)
                else Modifier.padding(start = 0.dp),
                style = MaterialTheme.typography.subtitle2
            )
            item.tags.forEach { tag ->
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colors.secondary),
                            MaterialTheme.shapes.medium
                        )
                        .padding(2.dp),
                    color = MaterialTheme.colors.secondary
                )
            }
            Text(
                text = item.niceDate,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primaryVariant
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Text(text = item.title, style = MaterialTheme.typography.subtitle1)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            if (item.type == 1) {
                Text(
                    text = "置顶",
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondary
                )
            }
            Text(
                text = "${item.superChapterName} / ${item.chapterName}",
                modifier = if (item.type == 1) Modifier.padding(start = 8.dp) else Modifier.padding(
                    start = 0.dp
                ),
                style = MaterialTheme.typography.subtitle2,
            )
        }
    }
}