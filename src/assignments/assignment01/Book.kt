package assignments.assignment01


/**
 * Represents a physical book in the library.
 *
 * Regular borrowing lasts seven days.
 */
class Book(
    val author: String,
    id: Int,
    title: String
) : LibraryItem(id, title) {

    // Default borrowing behavior required by LibraryItem.
    override fun borrowItem(
        userName: String,
        currentDate: String
    ) {
        borrowForDays(userName, currentDate, 7)
    }

    /**
     * Allows the borrowing duration to be customized.
     * This supports extended borrowing for premium users.
     */
    fun borrowForDays(
        userName: String,
        currentDate: String,
        days: Int
    ) {
        if (!isAvailable) {
            println("$title is not available.")
            return
        }

        // Calculate the date before changing the borrowing state.
        val calculatedDueDate = calculateDueDate(currentDate, days)

        isAvailable = false
        borrowedBy = userName
        dueDate = calculatedDueDate

        println("$userName borrowed $title by $author")
        println("Borrowed on: $currentDate")
        println("Due on: $dueDate")
    }
}
