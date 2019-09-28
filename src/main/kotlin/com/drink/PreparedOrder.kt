package com.drink

import com.OrderType

sealed class PreparedOrder(val orderType: OrderType) {

    companion object {
        fun getDrink(
            drink: OrderType?,
            sugarQuantity: Int?,
            stick: Boolean?,
            extraHot: Boolean?,
            message: String?
        ): PreparedOrder =
            when (drink) {
                OrderType.T -> Tea(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                OrderType.C -> Coffee(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                OrderType.H -> Chocolate(sugarQuantity ?: 0, stick ?: false, extraHot ?: false)
                OrderType.M -> Message(message ?: "")
                OrderType.O -> Orange
                else -> throw Exception("I don't know how to deal with $drink.")
            }
    }

}

data class Tea(val sugar: Int = 0, val stick: Boolean = false, val extraHot: Boolean = false) :
    PreparedOrder(OrderType.T)

data class Coffee(val sugar: Int = 0, val stick: Boolean = false, val extraHot: Boolean = false) :
    PreparedOrder(OrderType.C)

data class Chocolate(val sugar: Int = 0, val stick: Boolean = false, val extraHot: Boolean = false) :
    PreparedOrder(OrderType.H)

object Orange : PreparedOrder(OrderType.O)
data class Message(val message: String) : PreparedOrder(OrderType.M)