package us.myapplication.data

data class StocksUiState(
    val stocks: ArrayList<Stock> = ArrayList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessages: String? = ""
)
