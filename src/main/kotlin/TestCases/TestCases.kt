package TestCases

import domain.model.Transaction

fun main() {

}

// for add transaction
fun check(transactions: List<Transaction>, result: Int, correctResult: Int) {}

// for edit transaction
fun check(transaction: Transaction, result: Transaction, correctResult: Transaction) {}

// for delete transaction
fun check(id: Int, result: Boolean, correctResult: Boolean) {}