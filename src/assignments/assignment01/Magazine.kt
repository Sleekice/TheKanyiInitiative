package assignments.assignment01


/**
 * Represents a physical magazine.
 *
 * Magazines can be borrowed for three days.
 */
class Magazine(
    val issueNumber: Int,
    id: Int,
    title: String
) : LibraryItem(id, title) {

    override fun borrowItem(
        userName: String,
        currentDate: String
    ) {
        if (!isAvailable) {
            println("$title is not available.")
            return
        }

        val calculatedDueDate = calculateDueDate(currentDate, 3)

        isAvailable = false
        borrowedBy = userName
        dueDate = calculatedDueDate

        println("$userName borrowed $title, Issue $issueNumber")
        println("Borrowed on: $currentDate")
        println("Due on: $dueDate")
    }
}
