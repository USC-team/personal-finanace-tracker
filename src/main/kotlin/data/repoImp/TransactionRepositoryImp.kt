package data.repoImp

import domain.model.Transaction
import domain.repository.TransactionRepository

class TransactionRepositoryImp : TransactionRepository {
    var transactionsList : MutableList<Transaction> = mutableListOf();


    override fun add(transactions: List<Transaction>) : Boolean {
        if (transactions.isNotEmpty())
            transactionsList.addAll(transactions)

        return transactionsList.containsAll(transactions)
    }

    override fun update(model: Transaction): Boolean {
        TODO("Not yet implemented")
    }

}