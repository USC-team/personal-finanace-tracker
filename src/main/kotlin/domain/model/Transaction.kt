package domain.model

import java.rmi.server.UID

data class Transaction(
    val id: UID  ,
    val categories: Categories,
    val description : String,
    val amount : Double,
    val timeDate: String,
//    val transaction: MutableList<Transaction>
)
