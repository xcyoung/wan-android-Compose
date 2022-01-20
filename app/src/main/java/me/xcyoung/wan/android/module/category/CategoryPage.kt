package me.xcyoung.wan.android.module.category

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.ArticleVo
import me.xcyoung.wan.android.module.browser.BrowserActivity
import me.xcyoung.wan.android.ui.ArticleList

/**
 * @author ChorYeung
 * @since 2022/1/17
 */
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun CategoryPage(viewModel: CategoryViewModel) {
    val pagerState = rememberPagerState(
        initialPage = 0
    )
    val scopeState = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = pagerState.currentPage) {
                Tab(selected = pagerState.currentPage == 0, onClick = {
                    scopeState.launch {
                        pagerState.scrollToPage(0)
                    }
                }, text = {
                    Text(text = "体系")
                })
                Tab(selected = pagerState.currentPage == 1, onClick = {
                    scopeState.launch {
                        pagerState.scrollToPage(1)
                    }
                }, text = {
                    Text(text = "导航")
                })
            }
        }, content = {
            HorizontalPager(
                count = 2,
                state = pagerState,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 50.dp)
            ) { page ->
                when (page) {
                    0 -> TreePage(viewModel = viewModel)
                    1 -> NavPage(viewModel = viewModel)
                }
            }
        })
}

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
private fun TreePage(viewModel: CategoryViewModel) {
    val treeList by viewModel.treeListLiveData.observeAsState()
    val treeSelectedId by viewModel.treeListSelectedIdLiveData.observeAsState()
    val treeLazyPagingItems = viewModel.treePagingData.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.treeList()
    })

    Row(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(2f),
            content = {
                treeList?.forEach {
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxSize()
                                .padding(8.dp, 4.dp)
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.subtitle2)
                        }
                    }
                    items(it.children) { item ->
                        Box(
                            modifier = Modifier
                                .background(
                                    if (item.id != treeSelectedId) MaterialTheme.colors.primaryVariant
                                    else MaterialTheme.colors.background
                                )
                                .fillMaxSize()
                                .clickable {
                                    viewModel.treeListSelected(item)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (item.id != treeSelectedId) {
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(8.dp, 8.dp),
                                    style = MaterialTheme.typography.subtitle1
                                )
                            } else {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.subtitle1,
                                    modifier = Modifier.padding(8.dp, 8.dp),
                                    color = MaterialTheme.colors.secondary
                                )
                            }
                        }
                    }
                }
            })

        Box(
            modifier = Modifier
                .weight(3f)
        ) {
            ArticleList(lazyPagingItems = treeLazyPagingItems, content = {
                itemsIndexed(treeLazyPagingItems) { index, item ->
                    item ?: return@itemsIndexed
                    TreeArticleItem(item)
                    if (index < treeLazyPagingItems.itemCount - 1) {
                        Divider(
                            color = MaterialTheme.colors.primaryVariant,
                            thickness = 4.dp
                        )
                    }
                }
            })
        }
    }
}

@Composable
private fun TreeArticleItem(item: ArticleVo.ArticleItemVo) {
    val context: Context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .clickable {
                BrowserActivity.start(context, item.link)
            }
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (item.author.isEmpty()) item.shareUser else "${item.author}(Author)",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.subtitle2
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.niceDate,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.primaryVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@ExperimentalFoundationApi
@Composable
private fun NavPage(viewModel: CategoryViewModel) {
    val navList by viewModel.navListLiveData.observeAsState()
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = Unit, block = {
        viewModel.navList()
    })

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.primaryVariant),
            content = {
                navList?.forEach {
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .fillMaxSize()
                                .padding(8.dp, 4.dp)
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.subtitle2)
                        }
                    }
                    item {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            mainAxisSpacing = 4.dp,
                            crossAxisSpacing = 8.dp
                        ) {
                            it.articles.forEach {
                                Box(
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(4.dp))
                                        .background(MaterialTheme.colors.background)
                                        .clickable {
                                            BrowserActivity.start(context, it.link)
                                        }
                                ) {
                                    Text(
                                        text = it.title,
                                        modifier = Modifier.padding(4.dp),
                                        style = MaterialTheme.typography.subtitle1
                                    )
                                }
                            }
                        }
                    }
                }
            })
    }
}