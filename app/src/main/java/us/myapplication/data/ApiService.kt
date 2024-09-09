package us.myapplication.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("cash-homework/cash-stocks-api/portfolio.json")
    //suspend fun loadTransactions(): Stocks
    suspend fun loadTransactions(): Response<Stocks>

    @GET("cash-homework/cash-stocks-api/portfolio_malformed.json")
    suspend fun loadTransactionsMalformed(): Stocks

    @GET("cash-homework/cash-stocks-api/portfolio_empty.json")
    suspend fun loadTransactionsEmpty(): Stocks


    companion object {
        fun getService(): ApiService {
            return Retrofit.Builder().baseUrl("https://storage.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiService::class.java)
        }
    }
}