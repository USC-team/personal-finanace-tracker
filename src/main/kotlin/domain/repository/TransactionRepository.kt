package domain.repository

import domain.model.Categories
import domain.model.Report
import domain.model.Transaction

interface TransactionRepository {
    fun add(model: List<Transaction>): Boolean
    fun update(model: Transaction?): Boolean
    fun delete(model: Transaction): Boolean
    fun getAllTransactions(): List<Transaction>
    fun getMonthlyBalance(year: String, month: String): Double
    fun getMonthlyReport(year: String, month: String): Report
    fun getCategories(): List<Categories>
    fun deleteAllTransactions(): Boolean
    fun getTransactionDetails(id:Int): Boolean
}
