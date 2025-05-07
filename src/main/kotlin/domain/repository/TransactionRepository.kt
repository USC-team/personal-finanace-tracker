package domain.repository

import domain.model.Transaction

interface TransactionRepository {
    fun add(model: List<Transaction>): Boolean
    fun update(model: Transaction?) : Boolean
    fun delete(model: Transaction) : Boolean
    fun getAllTransactions() : List<Transaction>
}
