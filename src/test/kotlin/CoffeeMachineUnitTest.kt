import com.MoneyStatus
import com.OrderType
import com.command.verifyMoneyForOrder
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.test.assertEquals

class CoffeeMachineUnitTest : Spek({
    describe("given a drink and less money than cost") {
        it("return not ok and missing money") {
            assertEquals(Pair(MoneyStatus.NOT_OK, 0.2), verifyMoneyForOrder(OrderType.T, OrderType.T.cost - 0.2))
        }
    }

    describe("given a drink and more money than cost") {
        val c: Double = 4.0 + OrderType.T.cost
        it("return ok and change") {
            assertEquals(Pair(MoneyStatus.OK, 3.9999999999999996), verifyMoneyForOrder(OrderType.T, c))
        }
    }

    describe("given a drink and same money as cost") {
        it("return ok and change") {
            assertEquals(Pair(MoneyStatus.OK, 0.0), verifyMoneyForOrder(OrderType.T, OrderType.T.cost))
        }
    }
})
