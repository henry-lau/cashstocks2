package us.myapplication.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import us.myapplication.R

@Composable
fun OnboardingScreen(
    onStocksClicked: () -> Unit,
    onNoStockClicked: () -> Unit,
    onErrorClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.henry_test_app))
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onStocksClicked
        ) {
            Text(text = stringResource(R.string.stock_btn))
        }
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onNoStockClicked
        ) {
            Text(text = stringResource(R.string.no_stock_btn))
        }
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onErrorClicked
        ) {
            Text(text = stringResource(R.string.error_btn))
        }
    }
}