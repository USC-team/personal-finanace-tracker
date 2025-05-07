package cli

import domain.model.Categories
import domain.model.Report
import domain.model.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

const val RED_COLOR= "\u001B[31m"
const val MAGENTA_COLOR="\u001B[35m"
const val GREEN_COLOR= "\u001B[32m"
const val RESETCOLOR= "\u001B[0m"

fun addNewTransaction(categoriesList:MutableList<Categories>, transactionList:MutableList<Transaction>) :Transaction {

    println("$GREEN_COLOR You want to add a new transaction! $RESETCOLOR\n")
    println("First Things First!")
    val transactionId = transactionList.lastIndex + 2
    //enter Category ID
    val transactionCategory=chooseCategory(categoriesList)
    val transactionName=enterTransactionName()
    val transactionDescription = enterTransactionDescription()
    val transactionAmount=enterTransactionAmount()
    val transactionDateTimeConverted=enterTransactionDateTime()
    //creating the transaction
    val newTransaction= createTransaction(transactionId, transactionName, transactionDescription,
        transactionDateTimeConverted,transactionAmount,transactionCategory)

    return newTransaction
}
fun editTransaction(categoriesList: MutableList<Categories>, transactionList: MutableList<Transaction>): Transaction {
    var updatedTransaction:Transaction?
    var updatedTransactionCategory: Categories? = null
    var updatedTransactionName:String? = null
    var updatedTransactionDescription:String? = null
    var updatedTransactionAmount = 0.0
    var updatedTransactionDateTimeConverted: LocalDateTime?=null

    println("$GREEN_COLOR You want to edit a transaction! $RESETCOLOR\n")
    println("So! let's start by choosing the transaction's ID you want to edit")
    showTransactionList(transactionList)
    updatedTransaction= chooseTransaction(transactionList)

    println("Do you want to edit the category of the transaction? Y/N (No is the default value!)")
    if(readln().trim().uppercase() =="Y"){
        chooseCategory(categoriesList)
    }

    println("Do you want to edit the name of the transaction? Y/N (No is the default value!)")
    if(readln().trim().uppercase() =="Y"){
        updatedTransactionName=enterTransactionName()
    }
    println("Do you want to edit the description of the transaction? Y/N (No is the default value!)")
    if(readln().trim().uppercase() =="Y"){
        updatedTransactionDescription=enterTransactionDescription()
    }
    println("Do you want to edit the amount of the transaction? Y/N (No is the default value!)")
    if(readln().trim().uppercase() =="Y"){
        updatedTransactionAmount=enterTransactionAmount()
    }
    println("Do you want to edit the date and time of the transaction? Y/N (No is the default value!)")
    if(readln().trim().uppercase() =="Y"){
        updatedTransactionDateTimeConverted= enterTransactionDateTime()
    }
    if(updatedTransactionName==null){
        updatedTransactionName=updatedTransaction.name
    }
    if(updatedTransactionDescription==null){
        updatedTransactionDescription=updatedTransaction.description
    }
    if(updatedTransactionAmount==0.0){
        updatedTransactionAmount=updatedTransaction.amount
    }
    if(updatedTransactionCategory==null){
        updatedTransactionCategory=updatedTransaction.category
    }
    if(updatedTransactionDateTimeConverted==null){
        updatedTransactionDateTimeConverted=updatedTransaction.timeDate
    }
    updatedTransaction=createTransaction(updatedTransaction.id, updatedTransactionName,
        updatedTransactionDescription, updatedTransactionDateTimeConverted, updatedTransactionAmount,
        updatedTransactionCategory)

    return updatedTransaction
}
fun deleteTransaction(transactionList: MutableList<Transaction>):Transaction{
    println("$RED_COLOR You want to delete a transaction! $RESETCOLOR\n")
    println("So! let's start by choosing the transaction's ID you want to delete")
    showTransactionList(transactionList)
    return chooseTransaction(transactionList)
}

fun reportsCli(transactionList: MutableList<Transaction>, monthlySummary : MutableList<Transaction>, report: Report){
    while (true) {
        println(
            "Here's a List of Reports That You Can Explore!\n" +
                    "\t 1) View a Transaction's Details\n" +
                    "\t 2) View the Monthly Summary\n" +
                    "\t 3) View All Transactions\n" +
                    "\t 4) View the Monthly Balance Report\n" +
                    "\t 0) Back to the Main List"
        )
        val choice = readln()
        when (choice) {
            "0" -> {
                println("$MAGENTA_COLOR Back to the main list! See you again! $RESETCOLOR\n")
                return
            }

            "1" -> {
                println("Enter the id of the transaction u want to view details:")
                showTransactionList(transactionList)
                val transactionToBeViewed: Transaction=chooseTransaction(transactionList)
                showTransactionDetails(transactionToBeViewed)
                break
            }
            "2"->{
                println("The monthly summary of the current month is:")
                showTransactionList(monthlySummary)
                break
            }
            "3"->{
                println("The list of all your transactions is:")
                showTransactionList(transactionList)
                break
            }
            "4"->{
                println("The balance report of the current month is:")
                println("\tThe Total Income:      ${report.totalIncome}\n" +
                        "\tThe Total Expenses:    ${report.totalExpensive}\n" +
                        "\tThe Final Balance:     ${report.balance}\n")
                break
            }
        }
    }
}

fun enterTransactionName():String{
    println("Enter transaction name: ")
    return readln().trim()
}
fun enterTransactionDescription():String{
    println("Enter transaction description: ")
    return readln().trim()
}
fun enterTransactionAmount():Double{
    println("Enter transaction amount: ")
    while (true) {
        try {
            val transactionAmount = readln().trim().toDouble()
            if (transactionAmount>0.0) {
                return transactionAmount
            }
            else{
                "$RED_COLOR Not a valid amount. The amount should be larger than 0.0\n" +
                        "Try Again!$RESETCOLOR"
            }
        } catch (e: NumberFormatException) {
            println(
                "$RED_COLOR ${e.message}\n" +
                        "Try Again!$RESETCOLOR"
            )
        }
    }
}
fun enterTransactionDateTime():LocalDateTime{
    println(
        "Enter transaction date and time\n" +
        "It should be in the format (yyyy-MM-dd HH:mm)"
    )
    while (true) {
        try {
            val transactionDateTime = readln().trim()
            val transactionDateTimeConverted = LocalDateTime.parse(
                transactionDateTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            )
            return transactionDateTimeConverted
        } catch (e: DateTimeParseException) {
            println(
                "$RED_COLOR ${e.message}\n " +
                        "Try Again!$RESETCOLOR"
            )
        }
    }
}
fun createTransaction(transactionId:Int, transactionName:String, transactionDescription:String,
                      transactionDateTimeConverted:LocalDateTime,transactionAmount:Double,
                      transactionCategory:Categories ): Transaction {
    var newTransaction: Transaction? = null
    try {
        newTransaction = Transaction(
            id = transactionId,
            name = transactionName,
            description = transactionDescription,
            timeDate = transactionDateTimeConverted,
            amount = transactionAmount,
            category = transactionCategory
        )

        println(
            "$GREEN_COLOR Voilà! Your transaction is stored successfully!\n" +
                    showTransactionDetails(newTransaction)+ RESETCOLOR
        )

    } catch (e: Exception) {
        println(
            "$RED_COLOR ${e.message}\n$RESETCOLOR" +
                    "Try Again!"
        )
    }
    return newTransaction ?: throw IllegalStateException("Transaction creation failed.")
}
fun replaceTransaction(
    transactionList: MutableList<Transaction>,
    transactionId: Int,
    newTransaction: Transaction
) {
    val index = transactionList.indexOfFirst { it.id == transactionId }
    if (index != -1) {
        transactionList[index] = newTransaction
        println("Transaction with ID $transactionId was successfully edited.")
    } else {
        println("Transaction with ID $transactionId not found.")
    }
}
fun showCategoriesList(categoriesList: MutableList<Categories>) {
    for (category in categoriesList) {
        println("${category.id}     ${category.name}      ${category.type.name}")
    }
}
fun showTransactionList(transactionList: MutableList<Transaction>){
    for (transaction in transactionList) {
        println("${transaction.id}     ${transaction.name}      ${transaction.amount}")
    }
}
fun showTransactionDetails(transaction: Transaction){
    println("Transaction ID:           ${transaction.id} \n" +
            "Transaction Name:         ${transaction.name}\n" +
            "Transaction Description:  ${transaction.description}\n" +
            "Transaction Amount:       ${transaction.amount}\n" +
            "Transaction Date and Time:${transaction.timeDate}\n")
}
fun chooseTransaction(transactionList: MutableList<Transaction>):Transaction{
    while (true) {
        try {
            val transactionIdToBeEdited = readln().trim().toInt()
            val theTransaction= transactionList.filter { it-> it.id== transactionIdToBeEdited}
            //val theTransaction= getTransactionById(transactionList, transactionIdToBeEdited)
            if(theTransaction.isNotEmpty()) {
                return theTransaction[0]
            }
            else{
                println("$RED_COLOR This number isn't a valid transaction ID!\n" +
                        "Try Again!$RESETCOLOR")
            }
        } catch (e: NumberFormatException) {
            println(
                "$RED_COLOR ${e.message}\n" +
                        "Try Again!$RESETCOLOR"
            )
        }
    }
}
fun chooseCategory(categoriesList:MutableList<Categories>):Categories{
    println("Enter the number of the category:\n")
    showCategoriesList(categoriesList)
    while (true) {
        try {
            val categoryId = readln().trim().toInt()
            val theCategory=categoriesList.filter { it-> it.id==categoryId }
            //getCategoryById(categoriesList, categoryId)
            if(theCategory.isNotEmpty()) {
                return theCategory[0]
            }
            else{
                println("$RED_COLOR This number isn't a valid category number!\n" +
                        "Try Again!$RESETCOLOR")
            }
        } catch (e: NumberFormatException) {
            println(
                "$RED_COLOR ${e.message}\n" +
                        "Try Again!$RESETCOLOR"
            )
        }
    }
}

fun categoriesCli(){
    println(
        "Do you want to deal with categories or transactions?\n" +
                "\t 1) Categories\n" +
                "\t 2) Transactions\n" +
                "\t 0) Exit"
    )
    val choice1 = readln()
    when (choice1) {
        "1" -> {
            println("$MAGENTA_COLOR Great! Let's see what exactly you are thinking of! $RESETCOLOR\n")
            println(
                "Do you want to view, add, or edit categories\n" +
                        "\t 1) View Categories\n" +
                        "\t 2) Add a Category\n" +
                        "\t 3) Edit a Category\n"
            )
            return
        }

        "2" -> {
            println("$MAGENTA_COLOR Great! Let's see what exactly you are thinking of! $RESETCOLOR\n")
            println(
                "Do you want to view, add, or edit categories\n" +
                        "\t 1) View Categories\n" +
                        "\t 2) Add a Category\n" +
                        "\t 3) Edit a Category\n"
            )
            return
        }

        "0" -> {
            println("$MAGENTA_COLOR Bye Bye! See you again! $RESETCOLOR\n")
            return
        }

        else -> {
            println("${RED_COLOR}Wrong entry. Try again!$RESETCOLOR\n")
        }
    }
}