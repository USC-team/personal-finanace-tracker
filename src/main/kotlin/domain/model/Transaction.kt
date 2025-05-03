package domain.model

data class Transaction(
    val id: Int,
    val categories: Categories,
    val description : String,
    val amount : Double,
    val timeDate: String,
//    val transaction: MutableList<Transaction>
)
