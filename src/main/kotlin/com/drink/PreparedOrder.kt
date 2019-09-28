package com.drink

import com.OrderType
import com.command.OrderCommand

sealed class PreparedOrder(val orderType: OrderType) {

    companion object {
        fun getDrink(command: OrderCommand): PreparedOrder =
            when (command.drinkType) {
                OrderType.T -> Tea(command.sugarQuantity ?: 0, command.stick ?: false, command.extraHot ?: false)
                OrderType.C -> Coffee(command.sugarQuantity ?: 0, command.stick ?: false, command.extraHot ?: false)
                OrderType.H -> Chocolate(command.sugarQuantity ?: 0, command.stick ?: false, command.extraHot ?: false)
                OrderType.M -> Message(command.message ?: "")
                OrderType.O -> Orange
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