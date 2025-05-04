package TestCases

import domain.model.Categories
import domain.model.Report
import domain.model.Transaction
import domain.useCase.TransactionUseCase

fun main() {
    val transactionUseCase : TransactionUseCase = TransactionUseCase()
    ///add
    val tr1 = Transaction(
        id = 1,
        Categories(type = TransactionType.Expensive.name, id = 1, name = "Food"),
        description = "Food for home",
        amount = 20.0,
        timeDate = "03/05/2025"
    )
    val tr2 = Transaction(
        id = 2,
        Categories(type = TransactionType.Expensive.name, id = 2, name = "Transfer"),
        description = "Cost of transfer",
        amount = 50.0,
        timeDate = "03/05/2025"
    )
    val tr3 = Transaction(
        id = 3,
        Categories(type = TransactionType.Expensive.name, id = 3, name = "Rent"),
        description = "Pay The Rent ",
        amount = 8.0,
        timeDate = "03/05/2025"
    )

    var transactionsList = listOf(tr1, tr2, tr3)

    check(
        name = "When you add transactions successfully return",
        result = addTransaction(transactionUseCase.add(transactionsList)),
        correctResult = true
    )

    transactionsList = emptyList()

    check(
        name = "When you add transactions  not successfully because empty item return false",
        result = addTransaction(transactionUseCase.add(transactionsList)),
        correctResult = true
    )


    var transactions = Transaction(
        id = 1,
        categories = Categories(id = 1, name = "Food", type = "expensive"),
        description = "AHMED",
        amount = 100.0,
        timeDate = "02/05/2025"
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

    var listTransact: List<Transaction> = transactionsList.filter {
        it.timeDate.contains("05/2025")
    }
    check(name = "AdsadasDSDasdsd", result = showSummaryPerMonth(), correctResult = listTransact)

    val report: Report? = null
    check(name = "When No transaction found ", result = showPerMonthBalance(), correctResult = report)
}


fun <T> check(name: String, result: T, correctResult: T) = when {
    result == correctResult -> println("Success $name")
    else -> println("Filed  $name ")
}


fun addTransaction(addTransactions: Boolean): Boolean = addTransactions

fun updateTransaction(transaction: Transaction?, id: Int): Boolean {
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

enum class TransactionType {
    Income,
    Expensive
}