package machine

import jdk.nashorn.internal.runtime.NumberToString
import java.util.Scanner

abstract class Action() {
    protected fun <T> printRead(statement: String, readerFun: () -> T): T {
        println(statement)
        return readerFun.invoke()
    }

    abstract fun performAction(reader: Scanner)
}

fun main(args: Array<String>) {
    val coffeeMachine = CoffeeMachine()
    val reader = Scanner(System.`in`)
    loop@ while (true) {
        println("Write action (buy, fill, take, remaining, exit): ")
        when (reader.next()!!) {
            "buy" -> coffeeMachine.buy().performAction(reader)
            "fill" -> coffeeMachine.fill().performAction(reader)
            "take" -> coffeeMachine.take().performAction(reader)
            "remaining" -> println(coffeeMachine)
            "exit" -> break@loop
        }
    }

    reader.close()
}
