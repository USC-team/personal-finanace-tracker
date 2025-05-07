package domain.useCase

import domain.model.Categories
import domain.model.CategoryType
import domain.model.Report
import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionUseCase(private val repo: TransactionRepository) {
    fun add(transaction: List<Transaction>): Boolean = repo.add(transaction)
    fun update(transaction: Transaction?): Boolean = repo.update(transaction)
    fun delete(transaction: Transaction): Boolean = repo.delete(transaction)
    fun getAllTransactions(): List<Transaction> = repo.getAllTransactions()
    fun getMonthlyBalance(year: String, month: String): Double {
        val targetMonth = month.padStart(2, '0')
        return getAllTransactions()
            .filter { transaction ->
                transaction.date.year == year &&
                        transaction.date.month.padStart(2, '0') == targetMonth
            }
            .sumOf { it.amount }
    }
    fun getMonthlyTransactions(year: String, month: String): List<Transaction>{
        val targetMonth = month.padStart(2, '0')
        return getAllTransactions().filter { transaction ->
            transaction.date.year == year &&
                    transaction.date.month.padStart(2, '0') == targetMonth
        }
    }
    fun getMonthlyReport(year: String, month: String): Report {
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
    fun getCategories():List<Categories>{
        return listOf(Categories(id = 1, name = "Food", type = CategoryType.Expense.name),
            Categories(id = 2, name = "Rent", type = CategoryType.Expense.name),
            Categories(id = 3, name = "Salary", type = CategoryType.Income.name)
            )
    }
}
