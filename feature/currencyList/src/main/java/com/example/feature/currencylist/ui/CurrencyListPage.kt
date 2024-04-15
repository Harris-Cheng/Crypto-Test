package com.example.feature.currencylist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.domain.model.CurrencyModel
import com.example.feature.currencylist.R
import com.example.feature.currencylist.viewmodel.CurrencyListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrencyListPage() {
    Column(modifier = Modifier.fillMaxHeight()) {
        Spacer(
            Modifier.windowInsetsTopHeight(
                WindowInsets.systemBars
            )
        )
        SearchBarContent()
        Box(
            modifier = Modifier.weight(1f)
        ) {
            CurrencyListContent()
            EmptyView()
            ActionButtonContainer()
        }
    }
}

@Composable
fun SearchBarContent(viewModel: CurrencyListViewModel = koinViewModel()) {
    SearchBar(onTextChange = {
        viewModel.searchTerm(it)
    })
}

@Composable
fun SearchBar(onTextChange: (text: String) -> Unit) {
    val text = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text.value,
        onValueChange = {
            text.value = it
            onTextChange(it)
        },
        placeholder = {
            Text(text = "Search")
        },
        colors = TextFieldDefaults.colors().copy(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = TextFieldDefaults.shape,
        trailingIcon = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = ""
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
}

@Composable
fun CurrencyListContent(viewModel: CurrencyListViewModel = koinViewModel()) {
    val list by viewModel.displayList.collectAsState()
    CurrencyList(list = list)
}
@Composable
fun CurrencyList(list: List<CurrencyModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .imePadding()
    ) {
        itemsIndexed(
            list,
            key = { _, item ->
                item.name
            }
        ) { index, item ->
            CurrencyItem(model = item)
            if (index == list.size - 1) {
                Spacer(
                    Modifier.windowInsetsBottomHeight(
                        WindowInsets.systemBars
                    )
                )
                Spacer(
                    Modifier.height(130.dp)
                )
            }
        }
    }
}

@Composable
fun CurrencyItem(
    model: CurrencyModel
) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .size(40.dp)
                .background(
                    shape = RoundedCornerShape(
                        18.dp,
                    ),
                    color = Color(
                        "#373333".toColorInt()
                    ),
                )
                .wrapContentHeight(Alignment.CenterVertically),
            fontSize = 20.sp,
            text = model.shortName.take(1),
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterVertically),
            text = model.name,
            color = Color.Black,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = model.shortName,
            color = Color.Black,
            fontSize = 20.sp
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp),
            painter = painterResource(id = R.drawable.baseline_chevron_right_24),
            contentDescription = ""
        )
    }
}

@Composable
fun EmptyView(viewModel: CurrencyListViewModel = koinViewModel()) {
    val isEmpty by viewModel.isEmptyList.collectAsState()
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        ) {
        if (isEmpty) {
            Spacer(modifier = Modifier.height(50.dp))
            Icon(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.search_off),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "No Result",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ActionButtonContainer(viewModel: CurrencyListViewModel = koinViewModel()) {
    ActionButtonContent(
        onClearAll = { viewModel.onClearAllClick() },
        onInsertAll = { viewModel.onInsertAllClick() },
        onShowListA = { viewModel.onShowListAClick() },
        onShowListB = { viewModel.onShowListBClick() },
        onShowAll = { viewModel.onShowAllClick() }
    )
}

@Composable
fun ActionButtonContent(
    onClearAll: () -> Unit,
    onInsertAll: () -> Unit,
    onShowListA: () -> Unit,
    onShowListB: () -> Unit,
    onShowAll: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            createActionButtonItem("CLEAR ALL") {
                onClearAll()
            }
            createActionButtonItem("INSERT ALL") {
                onInsertAll()
            }
            createActionButtonItem("SHOW LIST A") {
                onShowListA()
            }
            createActionButtonItem("SHOW LIST B") {
                onShowListB()
            }
            createActionButtonItem("SHOW ALL") {
                onShowAll()
            }
        }
        Spacer(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.systemBars
            )
        )
    }
}

fun LazyGridScope.createActionButtonItem(buttonText: String, onClick: () -> Unit) {
    item {
        Button(
            modifier = Modifier
                .height(50.dp)
                .padding(horizontal = 5.dp, vertical = 5.dp),
            contentPadding = PaddingValues(all = 0.dp),
            onClick = onClick
        ) {
            Text(text = buttonText)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun ActionButtonContainerPreview() {
    ActionButtonContent(
        {},
        {},
        {},
        {},
        {},
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun SearchBarPreview() {
    SearchBar(onTextChange = {})
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CurrencyListPreview() {
    CurrencyList(
        list = listOf(
            CurrencyModel.Coin(
                "BTC",
                "Bitcoin",
                "BTC"
            ),
            CurrencyModel.Coin(
                "ETH",
                "Ethereum",
                "ETH"
            ),
            CurrencyModel.Coin(
                "ABC",
                "ABC",
                "ABC"
            ),
        )
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun CurrencyItemPreview() {
    CurrencyItem(
        model = CurrencyModel.Coin(
        "BTC",
        "Bitcoin",
        "BTC"
        )
    )
}