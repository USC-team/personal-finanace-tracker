package TestCases

import domain.model.Categories
import domain.model.CategoryType
import domain.model.Report
import domain.model.Transaction
import java.time.LocalDateTime

fun main() {
    ///add
    val tr1 = Transaction(
        id = 1,
        category = Categories(type = CategoryType.Expense, id = 1, name = "Food"),
        description = "Food for home",
        amount = 20.0,
        timeDate = LocalDateTime.of(2025,5, 3,0,0),
        name="Sandwiches"
    )
    val tr2 = Transaction(
        id = 2,
        category = Categories(type = CategoryType.Expense, id = 2, name = "Transfer"),
        description = "Cost of transfer",
        amount = 50.0,
        timeDate = LocalDateTime.of(2025,5, 3,0,0),
        name="For delivery"
    )
    val tr3 = Transaction(
        id = 3,
        category =Categories(type = CategoryType.Expense, id = 3, name = "Rent"),
        description = "Pay The Rent ",
        amount = 8.0,
        timeDate = LocalDateTime.of(2025,5, 3,0,0),
        name="april rent"
    )

    var transactionsList = listOf(tr1, tr2, tr3)

    check(
        name = "When you add transactions successfully return",
        result = addTransaction(transactionsList),
        correctResult = true
    )

    transactionsList = emptyList()

    check(
        name = "When you add transactions  not successfully because empty item return false",
        result = addTransaction(transactionsList),
        correctResult = true
    )


    var transactions = Transaction(
        id = 1,
        category = Categories(id = 1, name = "Food", type = CategoryType.Expense),
        description = "AHMED",
        amount = 100.0,
        timeDate = LocalDateTime.of(2025,5, 3,0,0),
        name="april rent"
    )
    check(
        name = "When update on transaction list and update successfully return false",
        result = updateTransaction(transaction = transactions, id = 6),
        correctResult = true
    )

    val emptyTransaction: Transaction? = null
    check(
        name = "When update and pass null transaction return false",
        result = updateTransaction(transaction = emptyTransaction, id = 0),
        correctResult = false
    )

    check(
        name = "When delete one transaction successfully return true",
        result = deleteTransaction(id = 1),
        correctResult = true
    )

    check(
        name = "When delete all transaction successfully return true",
        result = deleteTransaction(),
        correctResult = true
    )

    check(
        name = "When delete one transaction successfully return true",
        result = deleteTransaction(id = 600),
        correctResult = true
    )

    var listTransact: List<Transaction> = transactionsList.
    filter {it.timeDate.month == LocalDateTime.now().month &&
            it.timeDate.year == LocalDateTime.now().year}
    check(name = "AdsadasDSDasdsd", result = showSummaryPerMonth(), correctResult = listTransact)

    val report: Report? = null
    check(name = "When No transaction found ", result = showPerMonthBalance(), correctResult = report)
}


fun <T> check(name: String, result: T, correctResult: T) = when {
    result == correctResult -> println("Success $name")
    else -> println("Filed  $name ")
}


fun updateTransaction(transaction: Transaction?, id: Int): Boolean {
    return false
}

fun addTransaction(transactions: List<Transaction>): Boolean {
    return false

}

fun deleteTransaction(id: Int): Boolean {
    return false
}

fun deleteTransaction(): Boolean {
    return false
}


fun showSummaryPerMonth(): List<Transaction> {
    return emptyList()
}

fun showPerMonthBalance(): Report? {
    return null
}

enum class CategoryType {
    Income,
    Expensive
}