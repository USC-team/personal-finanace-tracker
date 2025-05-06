package cli

import domain.model.Categories
import domain.model.CategoryType
import domain.model.Transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofLocalizedDateTime
import java.time.format.DateTimeParseException
import java.util.*

const val RED_COLOR= "\u001B[31m"
const val MAGENTA_COLOR="\u001B[35m"
const val GREEN_COLOR= "\u001B[32m"
const val RESETCOLOR= "\u001B[0m"


fun main() {
    /**
     * @TODO @Mohammed
     * I'm waiting from the use case to pass to me a list of categories,
     * the category object should be identical in properties to Categories class
     * (only the id should be the index+1)
     * and there should be some dummy data in the list
     */
    /**
     * @TODO @Mohammed
     * same thing for transaction
     * and there should be a function
     */
    //initially I'm dealing directly with the models which I know is wrong!

    val categoriesList: MutableList<Categories> = mutableListOf()
    val transactionList: MutableList<Transaction> = mutableListOf()
    val newTransactioList: MutableList<Transaction> = mutableListOf()
    var newTransaction: Transaction?= null
    categoriesList.add(Categories(id = 1, name = "Food", type = CategoryType.Expense))

    println("$MAGENTA_COLOR***Welcome in USC Personal Finance Tracker***$RESETCOLOR\n")
    while (true) {
        println(
            "What action do you wnt to do?\n" +
                    "\t 1) Add a new Transaction\n" + //New Transactions (List)
                    "\t 2) Edit a Transaction\n" +
                    "\t 3) Delete a Transaction\n" +
                    "\t 4) Show the List of Available Reports\n" +
                    "\t 0) Exit"
        )
        var choice = readln()
        when (choice) {
            "0" ->{
                println("$MAGENTA_COLOR Bye Bye! See you again! $RESETCOLOR\n")
                return
            }
            "1" -> {
                newTransaction = addNewTransaction(categoriesList, transactionList)
                if (newTransaction != null) {
                    newTransactioList.add(newTransaction)
                    transactionList.add(newTransaction)
                    break
                }
                else{
                    println("$RED_COLOR Something went wrong!\n" +
                            "We couldn't save your transaction.\n" +
                            "Try Again!$RESETCOLOR")
                }
            }
            "2" ->{
                if(transactionList.isNotEmpty()) {
                    newTransaction = editTransaction(categoriesList, transactionList)
                    if (newTransaction != null) {
                        replaceTransaction(transactionList, newTransaction.id, newTransaction!!)
                        break
                    } else {
                        println(
                            "$RED_COLOR Something went wrong!\n" +
                                    "We couldn't edit your transaction.\n" +
                                    "Try Again!$RESETCOLOR"
                        )
                    }
                }
                else {
                    println(
                        "$RED_COLOR You don't have transactions Yet!\n" +
                                "We can't edit something that is not found!!\n$RESETCOLOR"
                    )
                }
            }
            "3"-> {
                if (transactionList.isNotEmpty()) {
                    newTransaction = deleteTransaction(transactionList)
                    if (newTransaction != null) {
                        transactionList.remove(newTransaction)
                        break
                    } else {
                        println(
                            "$RED_COLOR Something went wrong!\n" +
                                    "We couldn't delete your transaction.\n" +
                                    "Try Again!$RESETCOLOR"
                        )
                    }
                } else {
                    println(
                        "$RED_COLOR You don't have transactions Yet!\n" +
                                "We can't delete something that is not found!!\n$RESETCOLOR"
                    )
                }
            }
            "4"->{
                reportsCli()
                /**@TODO @Ala
                 * show a list of available reports
                 */
            }
        }
    }
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
fun addNewTransaction(categoriesList:MutableList<Categories>, transactionList:MutableList<Transaction>) :Transaction {

    var newTransaction: Transaction? = null
    var transactionCategory: Categories? = null
    var transactionName:String? = null
    var transactionDescription:String? = null
    var transactionAmount: Double = 0.0
    var transactionDateTimeConverted: LocalDateTime
    var transactionDateTime: String = ""
    var transactionId = transactionList.lastIndex + 2

    println("$GREEN_COLOR You want to add a new transaction! $RESETCOLOR\n")
    println("First Things First!")

    //enter Category ID
    transactionCategory=chooseCategory(categoriesList)
    transactionName=enterTransactionName()
    transactionDescription = enterTransactionDescription()
    transactionAmount=enterTransactionAmount()
    transactionDateTimeConverted=enterTransactionDateTime()
    //creating the transaction
    newTransaction= createTransaction(transactionId, transactionName, transactionDescription,
        transactionDateTimeConverted,transactionAmount,transactionCategory)

    return newTransaction ?: throw IllegalStateException("Transaction creation failed.")
}

fun showCategoriesList(categoriesList: MutableList<Categories>) {
    for (category in categoriesList) {
        println("${category.id}     ${category.name}      ${category.type.name}")
    }
}

fun getCategoryById(categoriesList: MutableList<Categories>, categoryId:Int):Categories? {
    for (category in categoriesList) {
        if (categoryId == category.id) {
            return category
        }
    }
    return null
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
            if (transactionDateTimeConverted!=null){
                return transactionDateTimeConverted
            }
            else {
                println("$RED_COLOR Not a valid date\n" +
                        "Try Again!$RESETCOLOR")
            }
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
            category = transactionCategory!!
        )

        println(
            "$GREEN_COLOR Voilà! Your transaction is stored successfully!\n" +
                    "Transaction ID:           ${transactionId}\n" +
                    "Transaction Name:         $transactionName\n" +
                    "Transaction Description:  $transactionDescription\n" +
                    "Transaction Amount:       $transactionAmount\n" +
                    "Transaction Date and Time:$transactionDateTimeConverted\n$RESETCOLOR"
        )

    } catch (e: Exception) {
        println(
            "$RED_COLOR ${e.message}\n$RESETCOLOR" +
                    "Try Again!"
        )
    }
    return newTransaction ?: throw IllegalStateException("Transaction creation failed.")
}
fun editTransaction(categoriesList: MutableList<Categories>, transactionList: MutableList<Transaction>): Transaction {
    var updatedTransaction:Transaction?=null
    var updatedTransactionCategory: Categories? = null
    var updatedTransactionName:String? = null
    var updatedTransactionDescription:String? = null
    var updatedTransactionAmount: Double = 0.0
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
    if(updatedTransaction!=null)
    {
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
    }
    return updatedTransaction?: throw IllegalStateException("Transaction edition failed.")
}
fun showTransactionList(transactionList: MutableList<Transaction>){
    for (transaction in transactionList) {
        println("${transaction.id}     ${transaction.name}      ${transaction.amount}")
    }
}
fun getTransactionById(transactionList: MutableList<Transaction>, transactionId:Int):Transaction?{
    for (transaction in transactionList) {
        if (transactionId == transaction.id) {
            return transaction
        }
    }
    return null
}
fun showTransactionDetails(transaction: Transaction){
    println("Transaction ID:           ${transaction.id} \n" +
            "Transaction Name:         ${transaction.name}\n" +
            "Transaction Description:  ${transaction.description}\n" +
            "Transaction Amount:       ${transaction.amount}\n" +
            "Transaction Date and Time:${transaction.timeDate}\n")
}
fun chooseTransaction(transactionList: MutableList<Transaction>):Transaction?{
    while (true) {
        try {
            var transactionIdToBeEdited = readln().trim().toInt()
            val theTransaction= transactionList.filter { it-> it.id== transactionIdToBeEdited}
            //val theTransaction= getTransactionById(transactionList, transactionIdToBeEdited)
            if(theTransaction!=null) {
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
            var categoryId = readln().trim().toInt()
            val theCategory=categoriesList.filter { it-> it.id==categoryId }
            //getCategoryById(categoriesList, categoryId)
            if(theCategory!=null) {
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
fun deleteTransaction(transactionList: MutableList<Transaction>):Transaction{
    println("$RED_COLOR You want to delete a transaction! $RESETCOLOR\n")
    println("So! let's start by choosing the transaction's ID you want to delete")
    showTransactionList(transactionList)
    return chooseTransaction(transactionList)!!
}
fun reportsCli(){
    println("Here's a List of Reports That You Can Explore!\n" +
            "\t 1) View a Transaction's Details\n" +
            "\t 2) View the Monthly Summary\n" +
            "\t 3) View All Transactions\n" +
            "\t 4) View the Monthly Balance Report\n" +
            "\t 0) Back to the Main List" )
}
fun categoriesCli(){
    println(
        "Do you want to deal with categories or transactions?\n" +
                "\t 1) Categories\n" +
                "\t 2) Transactions\n" +
                "\t 0) Exit"
    )
    var choice1 = readln()
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