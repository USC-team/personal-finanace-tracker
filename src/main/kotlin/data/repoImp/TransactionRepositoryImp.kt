package data.repoImp

import domain.model.Categories
import domain.model.CategoryType
import domain.model.Report
import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionRepositoryImp : TransactionRepository {
    var transactionsList: MutableList<Transaction> = mutableListOf()
    val categoriesList=listOf(
        Categories(id = 1, name = "Food", type = CategoryType.Expense.name),
        Categories(id = 2, name = "Rent", type = CategoryType.Expense.name),
        Categories(id = 3, name = "Salary", type = CategoryType.Income.name)
    )

    override fun add(transactions: List<Transaction>): Boolean {
        if (transactions.isNotEmpty()) {
            val notAddedBefore = transactions.filter { item ->
                transactionsList.none { existedItem -> existedItem.id == item.id }
            }
            if (notAddedBefore.isNotEmpty()) {
                transactionsList.addAll(notAddedBefore)
            }
        }

        return transactionsList.containsAll(transactions)
    }

    override fun update(transaction: Transaction?): Boolean {
        val index = transactionsList.indexOfFirst { it.id == transaction?.id }
        if (index != -1) {
            if (transaction != null) {
                transactionsList[index] = transaction
            }
        }
        return transactionsList.contains(transaction)
    }

    override fun delete(transaction: Transaction): Boolean {
        transactionsList.removeIf { it.id == transaction.id }
        return !transactionsList.contains(transaction)
    }

    override fun getAllTransactions(): List<Transaction> = transactionsList
    override fun getCategories():List<Categories>{
        return categoriesList
    }
    override fun getMonthlyReport(year: String, month: String): Report {
        val targetMonth = month.padStart(2, '0')
        val filteredTransactions = getAllTransactions().filter { transaction ->
            transaction.date.year == year &&
                    transaction.date.month.padStart(2, '0') == targetMonth
        }

        val income = filteredTransactions.filter { it.amount > 0 }.sumOf { it.amount }
        val expense = filteredTransactions.filter { it.amount < 0 }.sumOf { it.amount }.let { -it }
        val balance = income - expense

        return Report(totalIncome = income, totalExpense = expense, balance = balance)
    }
    override fun getMonthlyBalance(year: String, month: String): Double {
        val targetMonth = month.padStart(2, '0')
        return getAllTransactions()
            .filter { transaction ->
                transaction.date.year == year &&
                        transaction.date.month.padStart(2, '0') == targetMonth
            }
            .sumOf { it.amount }
    }

}