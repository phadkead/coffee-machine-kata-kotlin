import com.CoffeeMachine
import com.OrderType
import com.drink.*
import com.drink.OrderResult.Failure
import com.drink.OrderResult.Success
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals


class CalculatorBDDTest : Spek({
    given("a command to Coffee machine with less money") {
        val coffeeMachine = CoffeeMachine()

        on("Receiving command O::0.3") {
            val result = coffeeMachine.process("O::0.3")
            it("should give error") {
                assertEquals(Failure("Please insert more 0.3 to get Orange"), result)
            }
        }

        on("Receiving command Ch::0.3") {
            val result = coffeeMachine.process("O::0.3")
            it("should give error") {
                assertEquals(Failure("Please insert more 0.3 to get Orange"), result)
            }
        }

        on("Receiving command T:1:0") {
            val result = coffeeMachine.process("T:1:0")
            it("should give Error message for money") {
                assertEquals(Failure("Please insert more 0.6 to get Tea"), result)
            }
        }

        on("Receiving command without sugar H:0:0") {
            val result = coffeeMachine.process("H:0:0")
            it("should give Error message for money") {
                assertEquals(Failure("Please insert more 0.5 to get Chocolate"), result)
            }
        }

        on("Receiving command without numbers H::") {
            val result = coffeeMachine.process("H::")
            it("fails and gives message to insert required amount") {
                assertEquals(Failure("Please insert more 0.5 to get Chocolate"), result)
            }
        }

        on("Receiving command for message M:message123") {
            val result = coffeeMachine.process("M:message123")
            it("displays the message with content") {
                assertEquals(Success(Message("message123")), result)
            }
        }
    }

    given("A command to coffee machine with more money") {
        val coffeeMachine = CoffeeMachine()

        on("Receiving command T:1:10") {
            val result = coffeeMachine.process("T:1:2")
            it("should give Tea") {
                assertEquals(Success(Tea(1, true)), result)
            }
        }

        on("Receiving command Ch::0.7") {
            val result = coffeeMachine.process("Ch::0.7")
            it("should give extraHot Chocolate") {
                assertEquals(Success(Coffee(0, stick = false, extraHot = true)), result)
            }
        }

        on("Receiving command O::10") {
            val result = coffeeMachine.process("O::10")
            it("should give Orange") {
                assertEquals(Success(Orange), result)
            }
        }

        on("Receiving command without sugar, enough money H:0:0.5") {
            val result = coffeeMachine.process("H:0:0.5")
            it("should give chocolate without stick") {
                assertEquals(Success(Chocolate(0, false)), result)
            }
        }
    }

    given("A command to coffee machine with same as required money") {
        val coffeeMachine = CoffeeMachine()

        on("Receiving command T:1:0.6") {
            val result = coffeeMachine.process("T:1:2")
            it("should give Tea") {
                assertEquals(Success(Tea(1, true)), result)
            }
        }

        on("Receiving command without sugar H:0:0.5") {
            val result = coffeeMachine.process("H:0:0.5")
            it("should give Coffee without stick") {
                assertEquals(Success(Chocolate(0, false)), result)
            }
        }

    }

    given("The coffee machine ") {
        val coffeeMachine = CoffeeMachine()

        on("asked for summary") {
            coffeeMachine.process("H:0:0.5")
            coffeeMachine.process("T:0:0.6")
            coffeeMachine.process("T:0:0.6")
            coffeeMachine.process("O:0:0.8")
            coffeeMachine.process("C:0:0.7")
            coffeeMachine.process("C:0:0.7")

            val a = mapOf( OrderType.T to 2,  OrderType.H to 1 )

            it("should give total number of each item sold") {
                val actual = coffeeMachine.summary()
                assertEquals(2, actual.get(OrderType.T))
                assertEquals(0, actual.get(OrderType.M))
                assertEquals(1, actual.get(OrderType.O))
                assertEquals(2, actual.get(OrderType.C))
                assertEquals(1, actual.get(OrderType.H))
            }

            it("should give total money earned so far") {
                val actual = coffeeMachine.totalMoneyEarned()
                assertEquals(3.1, actual)

            }
        }
    }

})