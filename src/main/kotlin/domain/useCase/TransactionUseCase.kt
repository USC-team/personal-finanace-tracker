package domain.useCase

import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionUseCase(private val repo: TransactionRepository) {
    fun add(transaction: List<Transaction>): Boolean = repo.add(transaction)
    fun update(transaction: Transaction?): Boolean = repo.update(transaction)
    fun delete(transaction: Transaction): Boolean = repo.delete(transaction)
    fun getAllTransactions(): List<Transaction> = repo.getAllTransactions()
    fun getMonthlyBalance(year: String, month: String) {
        var list =getAllTransactions()
    }

    fun getMonthlyReport() {
        var list =getAllTransactions()
    }
}
