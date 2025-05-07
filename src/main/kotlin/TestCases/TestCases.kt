package TestCases

import domain.model.*
import java.time.LocalDateTime

fun main() {
    val transactionsList = listOf(
        Transaction(
            id = 1,
            categories = Categories(type = CategoryType.Expense.name, id = 1, name = "Food"),
            description = "Food for home",
            amount = 20.0,
            date =  Date(day = "02", month = "05", year = "2025"),
            name="Sandwiches"
        ),
        Transaction(
            id = 2,
            categories = Categories(type = CategoryType.Expense.name, id = 2, name = "Transfer"),
            description = "Cost of transfer",
            amount = 50.0,
            date =  Date(day = "02", month = "05", year = "2025"),
            name="For delivery"
        ),
        Transaction(
            id = 3,
            categories =Categories(type = CategoryType.Expense.name, id = 3, name = "Rent"),
            description = "Pay The Rent ",
            amount = 8.0,
            date =  Date(day = "02", month = "05", year = "2025"),
            name="april rent"
        ),
        Transaction(
            id = 4,
            categories =Categories(type = CategoryType.Expense.name, id = 3, name = "Rent"),
            description = "Pay The Rent ",
            amount = 8.0,
            date =  Date(day = "02", month = "05", year = "2024"),
            name="april rent"
        ),
        Transaction(
            id = 5,
            categories =Categories(type = CategoryType.Expense.name, id = 3, name = "Rent"),
            description = "Pay The Rent ",
            amount = 8.0,
            date =  Date(day = "02", month = "04", year = "2025"),
            name="april rent"
        )
    )
    val transactionsFromOtherMonths= listOf(
        Transaction(
            id = 5,
            categories =Categories(type = CategoryType.Expense.name, id = 3, name = "Rent"),
            description = "Pay The Rent ",
            amount = 8.0,
            date =  Date(day = "02", month = "04", year = "2025"),
            name="april rent"
        )
    )
    val transactionsFromOtherYears= listOf(
        Transaction(
            id = 5,
            categories =Categories(type = CategoryType.Expense.name, id = 3, name = "Rent"),
            description = "Pay The Rent ",
            amount = 8.0,
            date =  Date(day = "02", month = "05", year = "2025"),
            name="april rent"
        )
    )
    val transactionsListContainsNull = listOf(
        Transaction(
            id = 1,
            categories = Categories(type = CategoryType.Expense.name, id = 1, name = "Food"),
            description = "Food for home",
            amount = 20.0,
            date =  Date(day = "02", month = "05", year = "2025"),
            name="Sandwiches"
        ),null)

    val emptyTransactionsList : List<Transaction> = emptyList()
    val transaction = Transaction(
        id = 1,
        categories = Categories(id = 1, name = "Food", type = CategoryType.Expense.name),
        description = "AHMED",
        amount = 100.0,
        date = Date(day = "02", month = "05", year = "2025"),
        name="april rent"
    )
    val emptyTransaction: Transaction? = null
    val listTransactinsCurrentMonth: List<Transaction> = transactionsList.filter {
        it.date.month.contains("05") && it.date.year.contains("2025")}
    val nullReport: Report? = null
    val report= Report(8000.0,1000.0,7000.0)

    check(
        name = "When view the list of transactions while there are transactions stored, return the list of all transactions",
        result = viewAllTransactions(transactionsList),
        correctResult = transactionsList
    )
    check(
        name = "When view the list of transactions while there are no transactions stored, show an error message",
        result = viewAllTransactions(emptyTransactionsList),
        correctResult = emptyTransactionsList
    )
    check(
        name = "When view the details of a transaction that is found, print the details and return true",
        result = viewTransactionDetails(transactionsList,1),
        correctResult = true
    )
    check(
        name = "When view the details of a transaction that is not found, print error message and return false",
        result = viewTransactionDetails(transactionsList,100),
        correctResult = false
    )
    check(
        name = "When view the details of a transaction in an empty list, print error message and return false",
        result = viewTransactionDetails(emptyTransactionsList,1),
        correctResult = false
    )

    check(
        name = "When you add a transaction successfully, return true",
        result = addTransaction(transactionsList,listOf(transaction)),
        correctResult = true
    )
    check(
        name = "When you add transactions list successfully, return true",
        result = addTransaction(transactionsList, transactionsList),
        correctResult = true
    )

    check(
        name = "When you try to add an empty transactions list, return false",
        result = addTransaction(transactionsList, emptyTransactionsList),
        correctResult = false
    )

    check(
        name = "When you try to add an transactions list that contains a null transaction, add the non null transactions only" +
                "and return true",
        result = addTransaction(transactionsList, transactionsListContainsNull),
        correctResult = true
    )

    check(
        name = "When update a transaction and update successfully, return true",
        result = updateTransaction(transaction = transaction, transactionsList, id = 1),
        correctResult = true
    )
    check(
        name = "When try to update a transaction that's not found, return false",
        result = updateTransaction(transaction = transaction, transactionsList, id = 100),
        correctResult = false
    )
    check(
        name = "When try to update a transaction in an empty transaction list, return false",
        result = updateTransaction(transaction = transaction, emptyTransactionsList, id = 1),
        correctResult = false
    )

    check(
        name = "When update and pass null transaction, return false",
        result = updateTransaction(transaction = emptyTransaction, transactionsList, id = 1),
        correctResult = false
    )

    check(
        name = "When delete one transaction successfully, return true",
        result = deleteTransaction(transactionsList,id = 1),
        correctResult = true
    )

    check(
        name = "When delete all transaction successfully, return true",
        result = deleteTransaction(transactionsList),
        correctResult = true
    )

    check(
        name = "When try to delete a transaction that's not found, return false",
        result = deleteTransaction(transactionsList, id = 600),
        correctResult = false
    )
    check(
        name = "When try to delete a transaction from an empty list, return false",
        result = deleteTransaction(emptyTransactionsList, id = 1),
        correctResult = false
    )

    check(
        name = "When view the monthly summary successfully, return the list of transactions from same month and year",
        result = showSummaryPerMonth(transactionsList),
        correctResult = listTransactinsCurrentMonth
    )

    check(
        name = "When view the monthly summary while there are no transactions, print error message and return empty list",
        result = showSummaryPerMonth(emptyTransactionsList),
        correctResult = emptyTransactionsList
    )

    check(
        name = "When view the monthly summary while there are no transactions in the same month, print error message and return empty list",
        result = showSummaryPerMonth(transactionsFromOtherMonths),
        correctResult = emptyTransactionsList
    )

    check(
        name = "When view the monthly summary while there are no transactions in the same year, print error message and return empty list",
        result = showSummaryPerMonth(transactionsFromOtherYears),
        correctResult = emptyTransactionsList
    )

    check(
        name = "When view the monthly balance successfully, return the balance of transactions from same month and year",
        result = showBalancePerMonth(transactionsList),
        correctResult = report
    )

    check(
        name = "When view the monthly balance while there are no transactions, print error message and return empty report",
        result = showBalancePerMonth(emptyTransactionsList),
        correctResult = nullReport
    )

    check(
        name = "When view the monthly balance while there are no transactions in the same month, print error message and return empty report",
        result = showBalancePerMonth(transactionsFromOtherMonths),
        correctResult = nullReport
    )

    check(
        name = "When view the monthly balance while there are no transactions in the same year, print error message and return empty report",
        result = showBalancePerMonth(transactionsFromOtherYears),
        correctResult = nullReport
    )
}


fun <T> check(name: String, result: T, correctResult: T) = when {
    result == correctResult -> println("Success $name")
    else -> println("Filed  $name ")
}

fun viewAllTransactions(transactionsList:List<Transaction>): List<Transaction> {
    return transactionsList
}

fun viewTransactionDetails(transactionsList:List<Transaction>,transactionId: Int): Boolean {
    return false
}

fun updateTransaction(transaction: Transaction?, transactions: List<Transaction>, id: Int): Boolean {
    return false
}

fun addTransaction(oldTransactions: List<Transaction>, newTransactions: List<Transaction?>): Boolean {
    newTransactions.filterNotNull()
    return false
}

fun deleteTransaction(transactions: List<Transaction>, id: Int): Boolean {
    return false
}

fun deleteTransaction(transactions: List<Transaction>): Boolean {
    return false
}


fun showSummaryPerMonth(transactionsList: List<Transaction>): List<Transaction> {
    return emptyList()
}

fun showBalancePerMonth(transactions: List<Transaction>): Report? {
    return null
}