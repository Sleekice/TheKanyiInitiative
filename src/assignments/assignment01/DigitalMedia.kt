package assignments.assignment01


/**
 * Represents a digital library resource.
 *
 * Digital files are directly accessible and are not borrowed.
 */
class DigitalMedia(
    val fileSize: Double,
    val format: String,
    id: Int,
    title: String
) : LibraryItem(id, title) {

    override fun borrowItem(
        userName: String,
        currentDate: String
    ) {
        // No borrowing transaction occurs.
        // Do not change availability, borrower, or due date.

        println("$title is digital media and does not require borrowing.")
        println("File format: $format")
        println("File size: $fileSize MB")
        println("Available for direct access: $isAvailable")
    }
}
