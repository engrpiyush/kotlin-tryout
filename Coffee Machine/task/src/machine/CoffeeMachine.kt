package machine

import java.util.Scanner

class CoffeeMachine (
        var water: Int = 400,
        var milk: Int = 540,
        var beans: Int = 120,
        var cups:Int = 9,
        var money: Int = 550 ) {

    data class Ingredients(val water: Int, val milk: Int = 0, val beans: Int) {
    }

    enum class CoffeeType(var ingredients: Ingredients, var money: Int) {
        E(Ingredients(250, beans = 16), 4),
        L(Ingredients(350, 75, 20), 7),
        C(Ingredients(200, 100, 12), 6);
    }

    fun buy() = object: Action() {
        override fun performAction(reader: Scanner) {

            val type =
                    when(printRead("What do you want to buy? " +
                            "1 - espresso, " +
                            "2 - latte, " +
                            "3 - cappuccino, " +
                            "back - to main menu",
                            reader::next
                    )) {
                        "1" -> CoffeeType.E
                        "2" -> CoffeeType.L
                        "3" -> CoffeeType.C
                        else -> null
                    }
            if (type != null && canMakeCoffee(type)) makeCoffee(type)
        }
    }

    fun fill() = object: Action() {
        override fun performAction(reader: Scanner) {
            val readerFun: () -> Int = reader::nextInt
            water += printRead("Write how many ml of water do you want to add: ", readerFun)
            milk += printRead("Write how many ml of water do you want to add: ", readerFun)
            beans += printRead("Write how many grams of coffee beans do you want to add: ", readerFun)
            cups += printRead("Write how many disposable cups of coffee do you want to add: ", readerFun)
        }
    }

    fun take() = object: Action() {
        override fun performAction(reader: Scanner) {
            println("I gave you $$money")
            money = 0
        }
    }

    private fun makeCoffee(type: CoffeeType) {
        this.water-=type.ingredients.water
        this.milk-=type.ingredients.milk
        this.beans-=type.ingredients.beans
        this.money+=type.money
        this.cups--
    }

    private fun canMakeCoffee(type: CoffeeType) =
        when {
            water < type.ingredients.water -> {
                println("Sorry, not enough water")
                false
            }
            milk < type.ingredients.milk -> {
                println("Sorry, not enough milk")
                false
            }
            beans < type.ingredients.beans -> {
                println("Sorry, not enough coffee beans")
                false
            }
            cups < 1 -> {
                println("Sorry, not enough cups")
                false
            }
            else -> {
                println("I have enough resources, making you a coffee!")
                true
            }
        }

    override fun toString() =
        """
            The coffee machine has:
            $water of water
            $milk of milk
            $beans of coffee beans
            $cups of disposable cups
            $money of money
        """.trimIndent()

}