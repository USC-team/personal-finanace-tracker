package cli

import domain.model.Categories
import domain.model.CategoryType
import domain.model.Report
import domain.model.Transaction

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
    val report= Report(1000.0,600.0,400.0)
    val monthlySummary: MutableList<Transaction> = mutableListOf()

    categoriesList.add(Categories(id = 1, name = "Food", type = CategoryType.Expense.name))

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
        val choice = readln()
        when (choice) {
            "0" ->{
                println("$MAGENTA_COLOR Bye Bye! See you again! $RESETCOLOR\n")
                return
            }
            "1" -> {
                val transactionToBeAdded = addNewTransaction(categoriesList, transactionList)
                newTransactioList.add(transactionToBeAdded)
                transactionList.add(transactionToBeAdded)
                break
            }
            "2" ->{
                if(transactionList.isNotEmpty()) {
                    val transactionToBeEdited = editTransaction(categoriesList, transactionList)
                    replaceTransaction(transactionList, transactionToBeEdited.id, transactionToBeEdited)
                    break
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
                    val transactionToBeDeleted = deleteTransaction(transactionList)
                    transactionList.remove(transactionToBeDeleted)
                    break
                } else {
                    println(
                        "$RED_COLOR You don't have transactions Yet!\n" +
                                "We can't delete something that is not found!!\n$RESETCOLOR"
                    )
                }
            }
            "4"->{
                reportsCli(transactionList,monthlySummary, report)
            }
        }
    }
}