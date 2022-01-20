package me.xcyoung.wan.android.module.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import me.xcyoung.wan.android.module.OfficialAndProjectPage
import me.xcyoung.wan.android.module.article.ArticlePage
import me.xcyoung.wan.android.module.article.ArticleViewModel
import me.xcyoung.wan.android.module.category.CategoryPage
import me.xcyoung.wan.android.module.category.CategoryViewModel
import me.xcyoung.wan.android.module.official.OfficialViewModel
import me.xcyoung.wan.android.module.project.ProjectViewModel
import me.xcyoung.wan.android.ui.theme.WanAndroidTheme

/**
 * @author ChorYeung
 * @since 2022/1/14
 */
class HomeActivity : ComponentActivity() {
    sealed class Item(val icon: ImageVector) {
        class Article : Item(Icons.Default.Home)
        class Category : Item(Icons.Default.Menu)
        class Official : Item(Icons.Default.Email)
        class Project : Item(Icons.Default.Star)
    }

    private val items = listOf(Item.Article(), Item.Category(), Item.Official(), Item.Project())

    @ExperimentalCoroutinesApi
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

    @ExperimentalCoroutinesApi
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
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    item.icon,
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
                    count = items.count(),
                    state = pagerState
                ) { pagerScope ->
                    when (items[pagerScope]) {
                        is Item.Article -> {
                            val articleViewModel: ArticleViewModel =
                                viewModel(factory = ArticleViewModel.Factory(scaffoldState))
                            ArticlePage(viewModel = articleViewModel)
                        }
                        is Item.Category -> {
                            val categoryViewModel: CategoryViewModel =
                                viewModel(factory = CategoryViewModel.Factory(scaffoldState))
                            CategoryPage(viewModel = categoryViewModel)
                        }
                        is Item.Official -> {
                            val officialViewModel: OfficialViewModel = viewModel()
                            OfficialAndProjectPage(viewModel = officialViewModel)
                        }
                        is Item.Project -> {
                            val projectViewModel: ProjectViewModel = viewModel()
                            OfficialAndProjectPage(viewModel = projectViewModel)
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