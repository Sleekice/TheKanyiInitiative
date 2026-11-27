package assignments.assignment01


/**
 * Premium users can borrow up to five items.
 *
 * Books receive a 14-day borrowing period.
 * Magazines retain their standard three-day period.
 */
class PremiumUser(
    name: String
) : LibraryUser(
    name = name,
    borrowLimit = 5
) {

    // Customize the duration without duplicating borrowing logic.
    override val bookLoanDays: Int = 14
}
