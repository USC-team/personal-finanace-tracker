package domain.model

import java.util.UUID

data class Transaction(
    var id: Int,
    var categories: Categories,
    var description : String,
    var amount : Double,
    var timeDate: String,
//    val transaction: MutableList<Transaction>
)
