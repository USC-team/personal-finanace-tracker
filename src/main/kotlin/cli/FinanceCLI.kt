package cli

import domain.model.Categories
import domain.model.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.DateTimeParseException

const val RED_COLOR= "\u001B[31m"
const val MAGENTA_COLOR="\u001B[35m"
const val GREEN_COLOR= "\u001B[32m"
const val RESETCOLOR= "\u001B[0m"
fun main() {
    val categoriesList: MutableList<Categories> = mutableListOf()
    val transactionList: MutableList<Transaction> = mutableListOf()
    println("$MAGENTA_COLOR***Welcome in USC Personal Finance Tracker***$RESETCOLOR\n")
    while (true) {
        println(
            "What action do you wnt to do?\n" +
                    "\t 1) Add a new Transaction\n" + //New Transactions (List)
                    "\t 2) Edit a Transaction\n" +
                    "\t 3) Delete a Transaction\n" +
                    "\t 4) Show the List of Available Reports\n" +
                    "\t 0) Exit"
        )
        var choice = readln()
        when (choice) {
            "1" -> {
                println("$GREEN_COLOR You want to add a new transaction! $RESETCOLOR\n")
                println(
                    "First Things First!\n" +
                            "Enter the number of the category"
                )
                for (category in categoriesList) {
                    println("${category.id}     ${category.name}      ${category.type.name}")
                }
                var transactionCategory: Categories? = null
                while (true) {
                    try {
                        var categoryId = readln().trim().toInt()
                        for (category in categoriesList) {
                            if (categoryId == category.id) {
                                transactionCategory = category
                                break
                            }
                        }
                        break
                    } catch (e: NumberFormatException) {
                        println(
                            "$RED_COLOR ${e.message}\n" +
                                    "Try Again!"
                        )
                    }
                }
                println("Enter transaction name: ")
                var transactionName = readln()
                println("Enter transaction description: ")
                var transactionDescription = readln()
                println("Enter transaction amount: ")
                var transactionAmount: Double = 0.0
                while (true) {
                    try {
                        transactionAmount = readln().trim().toDouble()
                        break
                    } catch (e: NumberFormatException) {
                        println(
                            "$RED_COLOR ${e.message}\n" +
                                    "Try Again!"
                        )
                    }
                    println(
                        "Enter transaction date and time" +
                                "It should be in the format (yyyy-MM-dd HH:mm)"
                    )
                    var transactionDateTimeConverted: LocalDateTime
                    var transactionDateTime: String = ""
                    while (true) {
                        try {
                            transactionDateTime = readln().trim()
                            transactionDateTimeConverted = LocalDateTime.parse(
                                transactionDateTime,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                            )
                            break
                        } catch (e: DateTimeParseException) {
                            println(
                                "$RED_COLOR ${e.message}\n" +
                                        "Try Again!"
                            )
                        }
                    }
                    var transactionId = transactionList.lastIndex + 2
                    try {
                        var newTransaction = Transaction(
                            id = transactionId,
                            name = transactionName,
                            description = transactionDescription,
                            timeDate = transactionDateTimeConverted,
                            amount = transactionAmount,
                            category = transactionCategory!!
                        )

                        println(
                            "$GREEN_COLOR Voilà! Your transaction is stored successfully!\n" +
                                    "Transaction ID:           ${transactionList.lastIndex + 2}\n" +
                                    "Transaction Name:         $transactionName\n" +
                                    "Transaction Description:  $transactionDescription\n" +
                                    "Transaction Amount:       $transactionAmount\n" +
                                    "Transaction Date and Time:$transactionDateTime\n$RESETCOLOR"
                        )
                    } catch (e: Exception) {
                        println(
                            "$RED_COLOR ${e.message}\n" +
                                    "Try Again!"
                        )
                    }
                }
            }
        }
    }
}
fun reportsCli(){
    println("\t 4) View a Transaction's Details\n" +
            "\t 5) View the Monthly Summary\n" +
            "\t 6) View All Transactions\n" +
            "\t 7) View the Monthly Balance Report\n" )
}
fun categoriesCli(){
    println(
        "Do you want to deal with categories or transactions?\n" +
                "\t 1) Categories\n" +
                "\t 2) Transactions\n" +
                "\t 0) Exit"
    )
    var choice1 = readln()
    when (choice1) {
        "1" -> {
            println("$MAGENTA_COLOR Great! Let's see what exactly you are thinking of! $RESETCOLOR\n")
            println(
                "Do you want to view, add, or edit categories\n" +
                        "\t 1) View Categories\n" +
                        "\t 2) Add a Category\n" +
                        "\t 3) Edit a Category\n"
            )
            return
        }

        "2" -> {
            println("$MAGENTA_COLOR Great! Let's see what exactly you are thinking of! $RESETCOLOR\n")
            println(
                "Do you want to view, add, or edit categories\n" +
                        "\t 1) View Categories\n" +
                        "\t 2) Add a Category\n" +
                        "\t 3) Edit a Category\n"
            )
            return
        }

        "0" -> {
            println("$MAGENTA_COLOR Bye Bye! See you again! $RESETCOLOR\n")
            return
        }

        else -> {
            println("${RED_COLOR}Wrong entry. Try again!$RESETCOLOR\n")
        }
    }
}

