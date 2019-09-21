package com.drink

sealed class PreparedOrder {
    companion object {
        fun getDrink(
            drink: String?,
            sugarQuantity: Int?,
            stick: Boolean?,
            extraHot: Boolean?,
            message: String?
        ): PreparedOrder =
            when (drink) {
                "T" -> Tea(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                "C" -> Coffee(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                "H" -> Chocolate(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                "M" -> Message(message ?: "")
                "O" -> Orange
                else -> throw Exception("I don't know how to deal with $drink.")
            }
    }
}

data class Tea(val sugar: Int = 0, val stick: Boolean = false, val extraHot: Boolean = false) : PreparedOrder()
data class Coffee(val sugar: Int = 0, val stick: Boolean = false, val extraHot: Boolean = false) : PreparedOrder()
data class Chocolate(val sugar: Int, val stick: Boolean, val extraHot: Boolean = false) : PreparedOrder()
object Orange : PreparedOrder()
data class Message(val message: String) : PreparedOrder()