package domain.model

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val name: String,
    val category: Categories,
    val description : String,
    val amount : Double,
    val timeDate: LocalDateTime,
//    val transaction: MutableList<Transaction>
)
