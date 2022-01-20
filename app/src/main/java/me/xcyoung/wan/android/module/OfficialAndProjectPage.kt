package me.xcyoung.wan.android.module

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.bean.OfficialListVo
import me.xcyoung.wan.android.module.article.ArticleItem
import me.xcyoung.wan.android.ui.ArticleList

/**
 * @author ChorYeung
 * @since 2022/1/20
 */
@ExperimentalCoroutinesApi
@ExperimentalPagerApi
@Composable
fun OfficialAndProjectPage(viewModel: OfficialAndProjectViewModel) {
    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val scopeState = rememberCoroutineScope()
    val chapters by viewModel.classificationLiveData.observeAsState(emptyList())

    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchClassification()
    })

    Scaffold(
        topBar = {
            if (chapters.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    modifier = Modifier.wrapContentWidth(),
                    edgePadding = 3.dp,
                ) {
                    chapters.forEachIndexed { index, item ->
                        Tab(
                            modifier = Modifier.padding(16.dp, 8.dp),
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scopeState.launch {
                                    pagerState.scrollToPage(index)
                                }
                            }) {
                            Text(text = item.name)
                        }
                    }
                }
            }
        }) {
        HorizontalPager(
            count = chapters.count(),
            state = pagerState,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 50.dp)
        ) { page ->
            OfficialAndProjectSubPage(viewModel = viewModel, item = chapters[page])
        }
    }
}

@Composable
private fun OfficialAndProjectSubPage(
    viewModel: OfficialAndProjectViewModel,
    item: OfficialListVo
) {
    val lazyPageItems = viewModel.createArticlePagingData(item.id).collectAsLazyPagingItems()

    ArticleList(lazyPagingItems = lazyPageItems, content = {
        itemsIndexed(lazyPageItems) { index, item ->
            item ?: return@itemsIndexed
            ArticleItem(item = item)
            if (index < lazyPageItems.itemCount - 1) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colors.primaryVariant
                )
            }
        }
    })
}