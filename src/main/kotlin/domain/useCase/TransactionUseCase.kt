package domain.useCase

import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionUseCase(private val repo: TransactionRepository) {
    fun add(model: List<Transaction>): Boolean = repo.add(model)
    fun update(model: Transaction) : Boolean = repo.update(model)
}
