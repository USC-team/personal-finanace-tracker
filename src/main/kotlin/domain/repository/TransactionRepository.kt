package domain.repository

import domain.model.Transaction

interface TransactionRepository {
    fun add(model: List<Transaction>): Boolean
    fun update(model: Transaction) : Boolean
}
