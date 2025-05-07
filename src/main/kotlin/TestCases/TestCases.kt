package TestCases

import data.repoImp.TransactionRepositoryImp
import domain.model.Categories
import domain.model.CategoryType
import domain.model.Date
import domain.model.Report
import domain.model.Transaction
import domain.useCase.TransactionUseCase

fun main() {
    val transactionUseCase = TransactionUseCase(repo = TransactionRepositoryImp())
    ///add
    val tr1 = Transaction(
        id = 1,
        categories = Categories(type = CategoryType.Income.name, id = 1, name = "Food"),
        description = "Food for home",
        amount = 20.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name = "Sandwiches"
    )
    val tr2 = Transaction(
        id = 2,
        categories = Categories(type = CategoryType.Expense.name, id = 2, name = "Transfer"),
        description = "Cost of transfer",
        amount = 50.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name = "For delivery"
    )
    val tr3 = Transaction(
        id = 3,
        categories = Categories(type = CategoryType.Income.name, id = 3, name = "Rent"),
        description = "Pay The Rent ",
        amount = 8.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name = "april rent"
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


    /**
     * Update one transaction
     */
    var transactions = Transaction(
        id = 6,
        categories = Categories(id = 1, name = "Food", type = CategoryType.Expense.name),
        description = "AHMED",
        amount = 100.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name = "april rent"
    )
    check(
        name = "When update on transaction list and update successfully return false",
        result = updateTransaction(updateTransaction = transactionUseCase.update(transactions)),
        correctResult = true
    )

    val emptyTransaction: Transaction? = null
    check(
        name = "When update and pass null transaction return false",
        result = updateTransaction(transactionUseCase.update(emptyTransaction)),
        correctResult = false
    )


    var trDelete = Transaction(
        id = 6,
        categories = Categories(id = 1, name = "Food", type = "expensive"),
        description = "AHMED",
        amount = 100.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name = "april rent"
    )
    check(
        name = "When delete one transaction successfully return true",
        result = deleteTransaction(transactionUseCase.delete(trDelete)),
        correctResult = true
    )


    var listTransact: List<Transaction> = transactionsList.filter {
        it.date.month.contains("05")
    }
    check(name = "AdsadasDSDasdsd", result = showSummaryPerMonth(), correctResult = listTransact)

    val report: Report? = null
    check(name = "When No transaction found ", result = transactionUseCase.getMonthlyReport(year = "2025", month = "02"), correctResult = report)
}


fun <T> check(name: String, result: T, correctResult: T) = when {
    result == correctResult -> println("Success $name")
    else -> println("Filed  $name ")
}


fun addTransaction(addTransactions: Boolean): Boolean = addTransactions

fun updateTransaction(updateTransaction: Boolean): Boolean {
    return false
}

fun deleteTransaction(deleteTransaction: Boolean): Boolean {
    return false
}


fun showSummaryPerMonth(): List<Transaction> {
    return emptyList()
}

fun showPerMonthBalance(): Report? {
    return null
}
