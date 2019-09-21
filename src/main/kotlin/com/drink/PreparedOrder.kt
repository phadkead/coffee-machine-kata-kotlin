package com.drink

sealed class PreparedOrder {
    companion object {
        fun getDrink(drink: String?, sugarQuantity: Int?, stick: Boolean?, message: String?): PreparedOrder =
            when (drink) {
                "T" -> Tea(sugarQuantity ?: 0, stick ?: false)
                "C" -> Coffee(sugarQuantity ?: 0, stick ?: false)
                "H" -> Chocolate(sugarQuantity ?: 0, stick ?: false)
                "M" -> Message(message ?: "")
                else -> throw Exception("I don't know how to deal with $drink.")
            }
    }
}

data class Tea(val sugar: Int = 0, val stick: Boolean = false) : PreparedOrder()
data class Coffee(val sugar: Int = 0, val stick: Boolean = false) : PreparedOrder()
data class Chocolate(val sugar: Int, val stick: Boolean) : PreparedOrder()
data class Message(val message: String) : PreparedOrder()