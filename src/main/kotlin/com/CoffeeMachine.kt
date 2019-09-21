package com

import com.drink.OrderResult
import com.drink.PreparedOrder

class CoffeeMachine {
    fun process(command: String): OrderResult {
        //TODO throw exception if order not in right format
        val splittedValues = command.split(":")
        val drink: String = splittedValues[0]
        val message =
            if (drink == OrderType.M.name) splittedValues[1]
            else null

        val sugarQuantity: Int? = splittedValues[1].toIntOrNull() ?: 0

        val stick = sugarQuantity?.compareTo(0) != 0

        val money = if (splittedValues.size > 2)
            splittedValues[2].toFloatOrNull()
        else
            0f
        val (result, coins) = validateMoney(OrderType.valueOf(drink), money)

        return when (result) {
            MoneyStatus.NOT_OK -> OrderResult.Failure(
                "Please insert more ${String.format(
                    "%.1f",
                    coins
                ).toDouble()} to get ${OrderType.valueOf(drink).drink}"
            )
            else -> OrderResult.Success(PreparedOrder.getDrink(drink, sugarQuantity, stick, message))
        }
    }
}

fun validateMoney(orderType: OrderType, money: Float?): Pair<MoneyStatus, Float> {
    val insertedCoin: Float = money ?: 0.0f

    return if (insertedCoin.compareTo(orderType.cost) >= 0) {
        val change = insertedCoin.minus(orderType.cost)
        Pair(MoneyStatus.OK, (change))
    } else {
        val required = orderType.cost.minus(insertedCoin)
        println(required)
        Pair(MoneyStatus.NOT_OK, required)
    }
}

enum class MoneyStatus() {
    OK, NOT_OK
}

enum class OrderType(val drink: String?, val cost: Float) {
    T("Tea", 0.6f), C("Coffee", 0.4f), H("Chocolate", 0.5f), M(null, 0.0f)
}
