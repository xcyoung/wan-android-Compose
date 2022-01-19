package me.xcyoung.wan.android.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.xcyoung.wan.android.ArticleVo

@Composable
fun ArticleList(
    isRefreshing: Boolean = false,
    lazyPagingItems: LazyPagingItems<ArticleVo.ArticleItemVo>,
    onRefresh: (() -> Unit) = {},
    content: LazyListScope.() -> Unit
) {
    val rememberSwipeRefreshState = rememberSwipeRefreshState(false)
    val context = LocalContext.current
    val loadState = lazyPagingItems.loadState

    LaunchedEffect(key1 = Unit, block = {
        onRefresh()
    })

    when {
        loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0 -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(60.dp), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                )
            }
            return
        }
        loadState.refresh is LoadState.Error -> {
            val err = lazyPagingItems.loadState.refresh as LoadState.Error
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        lazyPagingItems.refresh()
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        contentDescription = null
                    )
                    Text(
                        text = err.error.localizedMessage ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            return
        }
        else -> {
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState,
        onRefresh = {
            onRefresh()
            lazyPagingItems.refresh()
        }) {
        rememberSwipeRefreshState.isRefreshing =
            lazyPagingItems.loadState.refresh is LoadState.Loading || isRefreshing
        LazyColumn(
            content = {
                if (loadState.refresh !is LoadState.Error) {
                    content()
                }

                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            LoadingItem()
                        }
                    }
                    is LoadState.Error -> {
                        val err = lazyPagingItems.loadState.append as LoadState.Error
                        Toast.makeText(
                            context,
                            err.error.localizedMessage,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                    }
                }
            })
    }
}

@Composable
private fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .padding(10.dp)
                .height(50.dp)
        )
    }
}