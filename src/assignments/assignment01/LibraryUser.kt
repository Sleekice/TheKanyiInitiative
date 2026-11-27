package assignments.assignment01

import java.time.LocalDate

/**
 * Represents a registered library user.
 *
 * Regular users may borrow up to three items.
 * Users with overdue items cannot borrow more items.
 *
 * The class is open so PremiumUser can inherit from it.
 */
open class LibraryUser(
    val name: String,
    val borrowedItems: MutableList<LibraryItem> = mutableListOf(),
    val borrowLimit: Int = 3
) {

    // Subclasses can customize the borrowing period for books.
    protected open val bookLoanDays: Int = 7

    /**
     * Attempts to borrow a library item.
     *
     * A request is rejected when:
     * - The item is digital media.
     * - The user has overdue items.
     * - The borrowing limit has been reached.
     * - The item is already unavailable.
     */
    fun borrowItem(item: LibraryItem, currentDate: String) {

        // Digital media does not enter the borrowing system.
        if (item is DigitalMedia) {
            item.borrowItem(name, currentDate)
            return
        }

        // Users must return overdue items before borrowing again.
        if (isOverdue(currentDate)) {
            println("$name has overdue items and cannot borrow more.")
            return
        }

        // Enforce the maximum number of borrowed items.
        if (borrowedItems.size >= borrowLimit) {
            println("$name has reached the limit of $borrowLimit items.")
            return
        }

        // Prevent borrowing an item already in use.
        if (!item.isAvailable) {
            println("${item.title} is currently unavailable.")
            return
        }

        // Apply the correct borrowing behavior for each item type.
        when (item) {
            is Book -> item.borrowForDays(
                name,
                currentDate,
                bookLoanDays
            )

            else -> item.borrowItem(name, currentDate)
        }

        // Add the item only if the transaction succeeded.
        if (!item.isAvailable && item.borrowedBy == name) {
            borrowedItems.add(item)
            println("$name has ${borrowedItems.size} borrowed item(s).")
        }
    }

    /**
     * Returns an item using the current system date.
     */
    fun returnItem(item: LibraryItem) {
        returnItem(item, LocalDate.now().toString())
    }

    /**
     * Returns an item on a specified date.
     *
     * The supplied date allows the system to identify
     * whether the item was returned after its deadline.
     */
    fun returnItem(item: LibraryItem, currentDate: String) {

        // Ensure the user actually borrowed this item.
        if (!borrowedItems.contains(item)) {
            println("$name has not borrowed ${item.title}.")
            return
        }

        val returnDate = LocalDate.parse(currentDate)

        // Determine whether the item is being returned late.
        val deadline = item.dueDate?.let {
            LocalDate.parse(it)
        }

        val wasLate = deadline != null &&
                returnDate.isAfter(deadline)

        // Reset the item's state and remove it from the user list.
        item.returnItem()
        borrowedItems.remove(item)

        if (wasLate) {
            println("$name returned ${item.title} late on $currentDate.")
        } else {
            println("$name returned ${item.title} on $currentDate.")
        }
    }

    /**
     * Returns true when at least one borrowed item
     * has a due date earlier than currentDate.
     */
    fun isOverdue(currentDate: String): Boolean {

        val today = LocalDate.parse(currentDate)

        return borrowedItems.any { item ->

            val deadline = item.dueDate?.let {
                LocalDate.parse(it)
            }

            deadline != null && deadline.isBefore(today)
        }
    }
}
