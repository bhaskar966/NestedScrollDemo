package com.beehomie.nestedscrolldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.beehomie.nestedscrolldemo.ui.theme.NestedScrollDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NestedScrollDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        NestedScrollDemo()
                    }

                }
            }
        }
    }
}

/**
 * Demo Layout
 */
@Composable
fun NestedScrollDemo(
    modifier : Modifier = Modifier
){

    val scrollState = rememberLazyListState()
    val nestedScrollConnection = rememberForwardingScrollConnection(scrollState)

    LazyColumn(
        state = scrollState,
        modifier = modifier
    ) {
        item {
            HorizontalPager(
                state = rememberPagerState(pageCount = {3}),
            ) { horizontalPagerIndex ->
                HorizontalItem(
                    itemIndex = horizontalPagerIndex,
                    modifier = Modifier.height(200.dp)
                )
            }
        }

        item {
            FixedLazyVerticalGrid(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
            )
        }
    }

}


@Composable
fun FixedLazyVerticalGrid(
    modifier : Modifier = Modifier
){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(190.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp)
    ) {
        items(20) { verticalPagerIndex ->
            VerticalItem(itemIndex = verticalPagerIndex)
        }
    }
}

/**
 * Horizontal Item
 */
@Composable
fun HorizontalItem(
    itemIndex : Int,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Box(
            modifier =  Modifier.fillMaxSize()
        ){
            Text(
                text = "Horizontal Item $itemIndex",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * Vertical Item
 */

@Composable
fun VerticalItem(
    itemIndex : Int,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier
            .fillMaxSize()
            .height(300.dp)
            .padding(4.dp)
    ) {
        Box(
            modifier =  Modifier.fillMaxSize()
        ){
            Text(
                text = "Horizontal Item $itemIndex",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}