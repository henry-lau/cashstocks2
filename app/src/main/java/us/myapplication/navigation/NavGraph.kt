package us.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import us.myapplication.data.DataViewModel
import us.myapplication.view.*

@Composable
//fun NavGraph(navController: NavHostController, vm: DataViewModel = viewModel()) {
fun NavGraph(navController: NavHostController, vm: DataViewModel) {
    //https://vtsen.hashnode.dev/simple-jetpack-compose-navigation-example
    NavHost(
        navController = navController,
        startDestination = NavRoute.Main.path
    ) {
        composable(route = NavRoute.Main.path) {
            OnboardingScreen(
                onStocksClicked = { navController.navigate(NavRoute.Stocklist.path) },
                onNoStockClicked = { navController.navigate(NavRoute.NoStocks.path) },
                onErrorClicked = { navController.navigate(NavRoute.Error.path) }
            )
        }

        composable(route = NavRoute.Stocklist.path) {
            StocksScreen(
                onUiStateChanged = vm.uiState.collectAsState(),
                onCallApi = { vm.fetchStocks(StockApiType.StocksApi) },
                onBackPressed = { navController.popBackStack() },
                onNavigateToStock = { stockName,
                                    stockTicker ->
                    navController.navigate(NavRoute.OneStock.withArgs(stockName, stockTicker))
                }
            )
        }

        composable(route = NavRoute.NoStocks.path) {
            NoStockScreen(
                onUiStateChanged = vm.uiState.collectAsState(),
                onCallApi = { vm.fetchStocks(StockApiType.NoStockApi) },
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable(route = NavRoute.Error.path) {
            ErrorScreen(
                onUiStateChanged = vm.uiState.collectAsState(),
                onCallApi = { vm.fetchStocks(StockApiType.ErrorApi) },
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoute.OneStock.withArgsFormat(
                NavRoute.OneStock.stockName,
                NavRoute.OneStock.stockTicker
            ),
            arguments = listOf(
                navArgument(NavRoute.OneStock.stockName) {
                    type = NavType.StringType
                },
                navArgument(NavRoute.OneStock.stockTicker) {
                    type = NavType.StringType
                }
            )) { navBackStackEntry ->
            val args = navBackStackEntry.arguments

            args?.getString(NavRoute.OneStock.stockName)?.let {
                OneStockScreen(
                    stockName = it,
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}
