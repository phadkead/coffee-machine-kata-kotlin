package com

import com.drink.OrderResult
import com.drink.PreparedOrder

class CoffeeMachine {

    private val orders = mutableListOf<PreparedOrder>()

    fun process(orderString: String): OrderResult {
        val commands = orderString.split(":")
        val drink: String = commands[0][0].toString()
        val extraHot: Boolean = commands[0].length == 2
        val message = getIfMessageInCommand(drink, commands)
        val sugarQuantity: Int? = commands[1].toIntOrNull() ?: 0
        val stick = sugarQuantity?.compareTo(0) != 0
        val money = getMoneyFromCommand(commands)
        val (result, coins) = verifyMoneyForOrder(OrderType.valueOf(drink), money)

        return processOrder(result, coins, drink, sugarQuantity, stick, extraHot, message)
    }

    private fun getIfMessageInCommand(drink: String, splittedValues: List<String>) =
        if (drink == OrderType.M.name) splittedValues[1]
        else null

    private fun getMoneyFromCommand(splittedValues: List<String>): Float =
        if (splittedValues.size > 2)
            splittedValues[2].toFloatOrNull() ?: 0.0f
        else
            0.0f


    private fun processOrder(
        moneyStatus: MoneyStatus,
        coins: Float,
        drinkType: String,
        sugarQuantity: Int?,
        stick: Boolean,
        extraHot: Boolean,
        message: String?
    ): OrderResult {
        return when (moneyStatus) {
            MoneyStatus.NOT_OK -> OrderResult.Failure(
                "Please insert more ${String.format(
                    "%.1f",
                    coins
                ).toDouble()} to get ${OrderType.valueOf(drinkType).drink}"
            )
            else -> {
                val preparedOrder = PreparedOrder.getDrink(drinkType, sugarQuantity, stick, extraHot, message)
                orders.add(preparedOrder)
                return OrderResult.Success(preparedOrder)
            }
        }
    }

    fun allOrders(): List<PreparedOrder> = orders
    fun summary(): List<PreparedOrder> = TODO()
}

fun verifyMoneyForOrder(orderType: OrderType, insertedMoney: Float): Pair<MoneyStatus, Float> {

    return if (insertedMoney.compareTo(orderType.cost) >= 0) {
        val change = insertedMoney.minus(orderType.cost)
        Pair(MoneyStatus.OK, (change))
    } else {
        val required = orderType.cost.minus(insertedMoney)
        println(required)
        Pair(MoneyStatus.NOT_OK, required)
    }
}

enum class MoneyStatus {
    OK, NOT_OK
}

enum class OrderType(val drink: String?, val cost: Float) {
    T("Tea", 0.6f), C("Coffee", 0.4f), H("Chocolate", 0.5f), O("Orange", 0.6f),
    M(null, 0.0f)
}
