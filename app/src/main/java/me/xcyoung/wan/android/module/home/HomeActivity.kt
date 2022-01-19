package me.xcyoung.wan.android.module.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.module.article.ArticlePage
import me.xcyoung.wan.android.module.article.ArticleViewModel
import me.xcyoung.wan.android.module.category.CategoryPage
import me.xcyoung.wan.android.module.category.CategoryViewModel
import me.xcyoung.wan.android.ui.theme.WanAndroidTheme

/**
 * @author ChorYeung
 * @since 2022/1/14
 */
class HomeActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidTheme {
                HomePage()
            }
        }
    }

    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    @Composable
    fun HomePage() {
        val pagerState = rememberPagerState()
        val scopeState = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation {
                    for (index in 0 until 2) {
                        val icon = if (index == 0) {
                            Icons.Default.Home
                        } else {
                            Icons.Default.Menu
                        }
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    icon,
                                    contentDescription = null
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scopeState.launch {
                                    pagerState.scrollToPage(index)
                                }
                            })
                    }
                }
            },
            content = {
                HorizontalPager(
                    count = 2,
                    state = pagerState
                ) { pagerScope ->
                    when (pagerScope) {
                        0 -> {
                            val articleViewModel: ArticleViewModel =
                                viewModel(factory = ArticleViewModel.Factory(scaffoldState))
                            ArticlePage(viewModel = articleViewModel)
                        }
                        1 -> {
                            val categoryViewModel: CategoryViewModel =
                                viewModel(factory = CategoryViewModel.Factory(scaffoldState))
                            CategoryPage(viewModel = categoryViewModel)
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = scaffoldState.snackbarHostState
                ) { data ->
                    Snackbar(
                        snackbarData = data
                    )
                }
            })
    }
}