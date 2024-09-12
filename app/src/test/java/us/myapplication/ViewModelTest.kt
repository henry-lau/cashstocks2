package us.myapplication

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import us.myapplication.data.*
import us.myapplication.navigation.StockApiType
import utility.TestUtils.mokk

@RunWith(MockitoJUnitRunner::class)
class DataViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: DataViewModel
   // @Mock
    private lateinit var mainRepository: MainRepository
    private var apiService: ApiService = mokk()

    private val stocklist = arrayListOf(
        Stock("TWTR", "Twitter, Inc.", "USD", 3833, null, 1636657688),
        Stock("^GSPC", "S&P 500", "USD", 318157, 25, 1636657688)
    )
    private val stocks = Stocks()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        mainRepository = mock(MainRepository::class.java)
        //mainRepository = MainRepository(apiService)
        viewModel = DataViewModel( mainRepository)
        stocks.stocks = stocklist
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value

        assertTrue(gameUiState.isLoading)
        assertTrue(gameUiState.stocks.isEmpty())
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = NetworkState.Error<Stocks>("failed")
        viewModel.handleResponse(incorrectPlayerWord)
        val gameUiState = viewModel.uiState.value

        assertTrue(gameUiState.isError)
        assertTrue(gameUiState.stocks.isEmpty())
    }

    @Test
    fun gameViewModel_CorrectWordGuessed() {
        val correctPlayerWord = NetworkState.Success<Stocks>(stocks)
        viewModel.handleResponse(correctPlayerWord)
        val gameUiState = viewModel.uiState.value

        assertFalse(gameUiState.isLoading)
        assertFalse(gameUiState.stocks.isEmpty())
        assertEquals(gameUiState.stocks, stocks.stocks)
    }

//    @Test
//    fun getAllStocksTest() {
//        runBlocking {
////            given(apiService.loadTransactions()).willReturn(stocks)
////            given(mainRepository.getAllStocks())
////                .willReturn(NetworkState.Success(stocks))
//            Mockito.`when`(mainRepository.getAllStocks())
//                .thenReturn(NetworkState.Success(stocks))
//
//            viewModel.fetchStocks(StockApiType.StocksApi)
//            val result = viewModel._stocksState.value
//            assertEquals(stocks, result)
//        }
//    }

}