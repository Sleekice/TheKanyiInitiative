package assignments.assignment01

import java.time.LocalDate

/**
 * Calculates a due date from a starting date and loan duration.
 *
 * Expected input format: yyyy-MM-dd
 * Example: 2026-11-21
 *
 * Returns the calculated due date as a String.
 */
fun calculateDueDate(currentDate: String, days: Int): String {
    require(days >= 0) {
        "Borrowing duration cannot be negative."
    }

    val rentalDate = LocalDate.parse(currentDate)

    val futureDate = rentalDate.plusDays(days.toLong())

    return futureDate.toString()
}


fun main() {
    val dueDate = calculateDueDate("2026-11-14", 7)

    println("Due date: $dueDate")
}

