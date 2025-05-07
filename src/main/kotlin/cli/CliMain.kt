package cli

import data.repoImp.TransactionRepositoryImp
import domain.model.Categories
import domain.model.CategoryType
import domain.model.Report
import domain.model.Transaction
import domain.repository.TransactionRepository
import domain.useCase.TransactionUseCase

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
    val repo= TransactionRepositoryImp()
    val transactionUseCase = TransactionUseCase(repo)

    val categoriesList=transactionUseCase.getCategories()
    val transactionList=transactionUseCase.getAllTransactions()

    val report= transactionUseCase.getMonthlyReport("2025","05")
    val monthlySummary= transactionUseCase.getMonthlyTransactions("2025","05")


    //categoriesList.add(Categories(id = 1, name = "Food", type = CategoryType.Expense.name))

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
                //val transactionUseCase: TransactionUseCase= TransactionUseCase(TransactionRepository)

                return
            }
            "1" -> {
                val transactionToBeAdded = addNewTransaction(categoriesList, transactionList)
                if(transactionUseCase.add(listOf(transactionToBeAdded))){
                    break
                }
                else {
                    println(
                        "$RED_COLOR Error happened we couldn't save the transaction!\n$RESETCOLOR"
                    )
                }
            }
            "2" ->{
                if(transactionList.isNotEmpty()) {
                    val transactionToBeEdited = editTransaction(categoriesList, transactionList)
                    if(transactionUseCase.update(transactionToBeEdited)) {
                        break
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
                    val transactionToBeDeleted = deleteTransaction(transactionList)
                    if(transactionUseCase.delete(transactionToBeDeleted)) {
                        break
                    }
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