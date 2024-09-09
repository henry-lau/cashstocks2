package us.myapplication.navigation

enum class StockApiType {
    StocksApi,
    NoStockApi,
    ErrorApi
}

sealed class NavRoute(val path: String) {
    object Main : NavRoute("main")
    object Stocklist : NavRoute("stocks")
    object NoStocks : NavRoute("no_stock")
    object Error : NavRoute("error")
    object OneStock : NavRoute("stock") {
        val stockName = "stockName"
        val stockTicker = "stockTicker"
    }

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}