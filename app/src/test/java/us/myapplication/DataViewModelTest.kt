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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import us.myapplication.data.*
import utility.TestUtils.mokk

//https://medium.com/arabamlabs/andorid-unit-test-iii-3c7d6771a27f
//https://medium.com/huawei-developers/testing-the-modules-of-your-mvvm-clean-architecture-android-project-part-2-testing-the-use-d2b5ddf5380a

@RunWith(MockitoJUnitRunner::class)
class DataViewModelTest0 {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var apiService: ApiService = mokk()

    @Mock
    var progressVisibilityChanged: Observer<Int>? = null

    //private var stocksLiveData: StatefulLiveData<ArrayList<Stock>> = StatefulLiveData()

    private var stocksResponseObject: Stocks = Stocks()

    private val testDispatcher = TestCoroutineDispatcher()
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `test loading stage and verify to show an empty list`() = runBlocking {
//        stocksLiveData.setData(stocksResponseObject.stocks as ArrayList<Stock>)
//
//        val dataViewModel = DataViewModel(
//            apiService,
//            stocksLiveData,
//            testDispatcher
//        )
//        dataViewModel.progressVisibilityChanged().observeForever(progressVisibilityChanged!!)
//
//        dataViewModel.fetchStocks(NO_STOCK)
//        val result: LiveData<StatefulLiveData.State<ArrayList<Stock>>> = dataViewModel.getStocks()
//        val state: StatefulLiveData.State<ArrayList<Stock>>? = result.value
//
//        verify(progressVisibilityChanged)!!.onChanged(View.VISIBLE)
//        assert(state is StatefulLiveData.State.Ready)
//        assertEquals((state as StatefulLiveData.State.Ready).data, stocksResponseObject.stocks)
    }

    @Test
    fun `test to show a list stocks`() = runBlocking {
//        //given(apiService.loadTransactions()).willReturn(stocksResponseObject)
//        val stocks = arrayListOf(
//            Stock("TWTR", "Twitter, Inc.", "USD", 3833, null, 1636657688),
//            Stock("^GSPC", "S&P 500", "USD", 318157, 25, 1636657688)
//        )
//        stocksResponseObject.stocks = stocks
//        stocksLiveData.setData(stocksResponseObject.stocks as ArrayList<Stock>)
//        val dataViewModel = DataViewModel(
//            apiService,
//            stocksLiveData,
//            testDispatcher
//        )
//
//        dataViewModel.fetchStocks(STOCK)
//        val result: LiveData<StatefulLiveData.State<ArrayList<Stock>>> = dataViewModel.getStocks()
//        val state: StatefulLiveData.State<ArrayList<Stock>>? = result.value
//
//        assert(state is StatefulLiveData.State.Ready)
//        assertEquals((state as StatefulLiveData.State.Ready).data, stocksResponseObject.stocks)
    }

}