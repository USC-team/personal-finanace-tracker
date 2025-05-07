package domain.model

data class Transaction(
    var id: Int,
    val name: String,
    var categories: Categories,
    var description: String,
    var amount: Double,
    var date: Date,
)
