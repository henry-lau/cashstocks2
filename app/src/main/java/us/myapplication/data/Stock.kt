package us.myapplication.data

data class Stock(
    val ticker: String = "",
    val name: String = "",
    val currency: String = "",
    val current_price_cents: Int = 0,
    val quantity: Int?,
    val current_price_timestamp: Int = 0
)