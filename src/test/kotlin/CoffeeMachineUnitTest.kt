import com.MoneyStatus
import com.OrderType
import com.verifyMoneyForOrder
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.test.assertEquals

class CoffeeMachineUnitTest : Spek({
    describe("given a drink and less money than cost") {
        it("return not ok and missing money") {
            assertEquals(Pair(MoneyStatus.NOT_OK, 0.19999999f), verifyMoneyForOrder(OrderType.T, OrderType.T.cost - 0.2f))
        }
    }

    describe("given a drink and more money than cost") {
        it("return ok and change") {
            assertEquals(Pair(MoneyStatus.OK, 0.19999999f), verifyMoneyForOrder(OrderType.T, OrderType.T.cost + 0.2f))
        }
    }

    describe("given a drink and same money as cost") {
        it("return ok and change") {
            assertEquals(Pair(MoneyStatus.OK, 0.0f), verifyMoneyForOrder(OrderType.T, OrderType.T.cost))
        }
    }
})
