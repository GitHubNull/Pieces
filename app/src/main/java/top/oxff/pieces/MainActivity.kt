package top.oxff.pieces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import kotlinx.coroutines.launch
import top.oxff.pieces.database.AppDatabase
import top.oxff.pieces.day.PieceEarningsByDayViewModel
import top.oxff.pieces.month.PieceEarningsByMonthViewModel
import top.oxff.pieces.ui.BottomBar
import top.oxff.pieces.ui.day.DayScreen
import top.oxff.pieces.ui.month.MonthScreen
import top.oxff.pieces.ui.theme.PiecesTheme
import top.oxff.pieces.ui.year.YearView
import top.oxff.pieces.year.PieceEarningsByYearViewModel

@Suppress("NAME_SHADOWING", "UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "pieces.db"
        ).build()
    }

    private val dayViewModel by viewModels<PieceEarningsByDayViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PieceEarningsByDayViewModel(db.dao) as T
                }
            }
        })
    private val monthViewModel by viewModels<PieceEarningsByMonthViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PieceEarningsByMonthViewModel(db.dao) as T
                }
            }
        })

    private val yearViewModel by viewModels<PieceEarningsByYearViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PieceEarningsByYearViewModel(db.dao) as T
                }
            }
        })

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Database.connect("jdbc:sqlite:/data/data.db", "org.sqlite.JDBC")

        setContent {
            PiecesTheme {
                Column {

                    val dayState by dayViewModel.dateState.collectAsState()
                    val monthState by monthViewModel.pieceEarningsByMonthState.collectAsState()
                    val yearState by yearViewModel.pieceEarningsByYearState.collectAsState()

                    val pagerState = rememberPagerState()
                    val scope = rememberCoroutineScope()


                    HorizontalPager(
                        pageCount = 3,
                        Modifier.weight(1f),
                        state = pagerState
                    ) { page ->
                        when (page) {
                            0 -> DayScreen(dayState, dayViewModel::onEvent)
                            1 -> MonthScreen(monthState, monthViewModel::onEvent, dayViewModel::onEvent){ page ->
                                scope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                            2 -> YearView(yearState, yearViewModel::onEvent, monthViewModel::onEvent){page->
                                scope.launch {
                                    pagerState.animateScrollToPage(page)
                                }
                            }
                        }

                    }

                    BottomBar(pagerState.currentPage) { page ->
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }
                }
            }
        }

    }
}


