package us.myapplication.data

open class MainRepository (private val retrofitService: ApiService) {

    suspend fun getAllStocks() : NetworkState<Stocks> {
            val response = retrofitService.loadTransactions()
            return if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    NetworkState.Success(responseBody)
                } else {
                    NetworkState.Error(response.errorBody().toString())
                }
            } else {
                NetworkState.Error(response.errorBody().toString())
            }
        }

}