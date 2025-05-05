package data.repoImp

import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionRepositoryImp : TransactionRepository {
    var transactionsList: MutableList<Transaction> = mutableListOf()


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


}