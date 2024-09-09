package us.myapplication.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import us.myapplication.R
import us.myapplication.data.Stock
import us.myapplication.data.StocksUiState
import us.myapplication.theme.HenryTheme

@Composable
private fun LoadingContent(
    loadingContent: @Composable () -> Unit,
    callApi: () -> Unit
) {
    loadingContent()
    callApi()
}

@Composable
fun StocksScreen(
    onUiStateChanged: State<StocksUiState>,
    onCallApi: () -> Unit,
    onBackPressed: () -> Unit,
    onNavigateToStock: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.api), fontSize = 16.sp)
        DefaultButton(text = stringResource(R.string.back), onClick = onBackPressed)

        Surface(modifier) { ShowStocks(onUiStateChanged.value, onNavigateToStock) }

        LoadingContent(
            loadingContent = { ShowMessage(stringResource(R.string.loading)) },
            callApi = onCallApi
        )
    }
}

@Composable
fun NoStockScreen(
    onUiStateChanged: State<StocksUiState>,
    onCallApi: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.api), fontSize = 16.sp)
        DefaultButton(text = stringResource(R.string.back), onClick = onBackPressed)

        Surface(modifier) {
            if (onUiStateChanged.value.isEmpty) {
                ShowMessage(stringResource(R.string.no_stock))
            }
        }

        LoadingContent(
            loadingContent = { ShowMessage(stringResource(R.string.loading)) },
            callApi = onCallApi
        )
    }
}

@Composable
fun ErrorScreen(
    onUiStateChanged: State<StocksUiState>,
    onCallApi: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.api), fontSize = 16.sp)
        DefaultButton(text = stringResource(R.string.back), onClick = onBackPressed)

        Surface(modifier) {
            if (onUiStateChanged.value.isError) {
                ShowMessage(stringResource(R.string.error))
            }
        }

        LoadingContent(
            loadingContent = { ShowMessage(stringResource(R.string.loading)) },
            callApi = onCallApi
        )
    }
}

@Composable
private fun ShowStocks(
    stockUi: StocksUiState,
    onNavigateToStock: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = stockUi.stocks) { stockItem ->
            ShowStock(stock = stockItem, onNavigateToStock)
        }
    }
}

@Composable
fun ShowMessage(
    message: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message)
    }
}

@Composable
private fun ShowStock(
    stock: Stock,
    navigateToStock: (String, String) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { navigateToStock(stock.name, stock.ticker) })
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "${stock.name}, ")
                Text(
                    text = stock.ticker, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}

@Composable
fun OneStockScreen(
    stockName: String, onBackPressed: () -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.api), fontSize = 16.sp)
        DefaultButton(text = stringResource(R.string.back), onClick = onBackPressed)

        Surface(modifier) {
            ShowMessage(stockName)
        }

    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun LoadingScreenPreview() {
    HenryTheme {
        ShowMessage(stringResource(R.string.testig))
    }
}

@Preview
@Composable
fun ShowStocks() {
    HenryTheme {
        ShowStocks(
            StocksUiState(
                arrayListOf(Stock("ticker", "name", "currency", 99, 10, 100)),
                isLoading = false
            ),
            onNavigateToStock = { _, _ -> },
            Modifier.fillMaxSize()
        )
    }
}
