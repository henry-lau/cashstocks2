package us.myapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import us.myapplication.navigation.StockApiType

class DataViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val TIMEOUT_MILLIS = 5_000L
    private var _stocksState = MutableStateFlow(StocksUiState(isLoading = true))

    //https://medium.com/@mortitech/sharein-vs-statein-in-kotlin-flows-when-to-use-each-1a19bd187553
    val uiState: StateFlow<StocksUiState> = _stocksState.asStateFlow()
//    val uiState: StateFlow<StocksUiState> = _stocksState.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//        initialValue = StocksUiState(isLoading = true)
//    )

    fun handleResponse(state: NetworkState<Stocks>) {
        if (state is NetworkState.Success) {
            _stocksState.update {
                it.copy(
                    stocks = state.data.stocks as ArrayList<Stock>,
                    isLoading = false,
                    isEmpty = state.data.stocks.isEmpty()
                )
            }
        } else if (state is NetworkState.Error) {
            _stocksState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
//                    errorMessages = state.response.code().toString()
                    errorMessages = state.response
                )
            }
        }
    }

    fun fetchStocks(option: StockApiType) {
        viewModelScope.launch {
            val response = mainRepository.getAllStocks()
            handleResponse(response)
        }
    }
}
