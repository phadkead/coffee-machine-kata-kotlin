package com.command

import com.MoneyStatus
import com.OrderType

data class OrderCommand(
    val moneyStatus: MoneyStatus,
    val coins: Double,
    val drinkType: OrderType,
    val sugarQuantity: Int?,
    val stick: Boolean,
    val extraHot: Boolean,
    val message: String?
) {
    companion object Factory {
        fun create(orderString: String): OrderCommand {
            return getCommandFromOrderString(orderString)
        }
    }
}

private fun getCommandFromOrderString(orderString: String): OrderCommand {
    val commands = orderString.split(":")
    val drink: String = commands[0][0].toString()
    val extraHot: Boolean = commands[0].length == 2
    val message = getIfMessageInCommand(drink, commands)
    val sugarQuantity: Int? = commands[1].toIntOrNull() ?: 0
    val stick = sugarQuantity?.compareTo(0) != 0
    val money = getMoneyFromCommand(commands)
    val (result, coins) = verifyMoneyForOrder(OrderType.valueOf(drink), money)

    return OrderCommand(
        result,
        coins,
        OrderType.valueOf(drink),
        sugarQuantity,
        stick,
        extraHot,
        message
    )
}

fun verifyMoneyForOrder(orderType: OrderType, insertedMoney: Double): Pair<MoneyStatus, Double> {
    return if (insertedMoney.compareTo(orderType.cost) >= 0) {
        val change = insertedMoney.minus(orderType.cost)
        Pair(MoneyStatus.OK, (change))
    } else {
        val required = orderType.cost.minus(insertedMoney)
        println(required)
        Pair(MoneyStatus.NOT_OK, required)
    }
}

private fun getIfMessageInCommand(drink: String, splittedValues: List<String>) =
    if (drink == OrderType.M.name) splittedValues[1]
    else null

private fun getMoneyFromCommand(splittedValues: List<String>): Double =
    if (splittedValues.size > 2)
        splittedValues[2].toDoubleOrNull() ?: 0.0
    else
        0.0