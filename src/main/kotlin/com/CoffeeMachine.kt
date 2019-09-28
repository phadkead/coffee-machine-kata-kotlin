package com

import com.command.OrderCommand
import com.drink.OrderResult
import com.drink.PreparedOrder

class CoffeeMachine {

    private val orders = mutableListOf<PreparedOrder>()

    fun process(orderString: String): OrderResult {
        val command = OrderCommand.create(orderString)
        return processOrder(command)
    }

    fun summary(): Map<OrderType, Int> {
        return enumValues<OrderType>().map { orderType ->
            val count = orders.count { it.orderType == orderType }
            orderType to count
        }.toMap()
    }

    fun totalMoneyEarned(): Double {
        return orders.sumByDouble { it.orderType.cost.toDouble() }
    }

    private fun processOrder(command: OrderCommand): OrderResult {
        return when (command.moneyStatus) {
            MoneyStatus.NOT_OK -> OrderResult.Failure(
                "Please insert more ${String.format(
                    "%.1f",
                    command.coins
                ).toDouble()} to get ${command.drinkType.drink}"
            )
            else -> {
                val preparedOrder = PreparedOrder.getDrink(command)
                orders.add(preparedOrder)
                return OrderResult.Success(preparedOrder)
            }
        }
    }
}

enum class MoneyStatus {
    OK, NOT_OK
}

enum class OrderType(val drink: String?, val cost: Double) {
    T("Tea", 0.6), C("Coffee", 0.4), H("Chocolate", 0.5), O("Orange", 0.6),
    M(null, 0.0)
}
