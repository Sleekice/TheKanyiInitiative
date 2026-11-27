package assignments.assignment01


import java.time.LocalDateTime

/**
 * Represents a library employee.
 *
 * Librarian:
 * - Can add items.
 * - Can remove items.
 *
 * Assistant Librarian:
 * - Can add items.
 * - Cannot remove items.
 *
 * Every administrative attempt is recorded in actionLogs.
 */
class LibraryStaff(
    val name: String,
    val role: String,
    private val library: Library
) : AdminActions {

    val actionLogs: MutableList<String> = mutableListOf()

    /**
     * Records an administrative event with a timestamp.
     */
    private fun logAction(message: String) {
        val timestamp = LocalDateTime.now()
        actionLogs.add("[$timestamp] $message")
    }

    /**
     * Both Librarians and Assistant Librarians
     * are allowed to add library items.
     */
    override fun addItem(item: LibraryItem) {

        val canAdd = role.equals("Librarian", ignoreCase = true) ||
                role.equals("Assistant Librarian", ignoreCase = true)

        if (!canAdd) {
            val message = "$name was denied permission to add ${item.title}."
            println(message)
            logAction(message)
            return
        }

        // Ask the shared Library catalog to add the item.
        val success = library.addCatalogItem(item)

        if (success) {
            val message = "$name added '${item.title}' to the library."
            println(message)
            logAction(message)
        } else {
            val message = "$name could not add '${item.title}': duplicate ID."
            println(message)
            logAction(message)
        }
    }

    /**
     * Only Librarians can remove items.
     *
     * Assistant Librarians are denied permission.
     */
    override fun removeItem(itemId: Int) {

        if (!role.equals("Librarian", ignoreCase = true)) {
            val message = "$name was denied permission to remove item $itemId."
            println(message)
            logAction(message)
            return
        }

        val item = library.searchById(itemId)

        if (item == null) {
            val message = "$name could not find item $itemId."
            println(message)
            logAction(message)
            return
        }

        // Prevent removing an item that is currently borrowed.
        if (!item.isAvailable) {
            val message = "$name cannot remove '${item.title}' while borrowed."
            println(message)
            logAction(message)
            return
        }

        val success = library.removeCatalogItem(itemId)

        if (success) {
            val message = "$name removed '${item.title}' from the library."
            println(message)
            logAction(message)
        }
    }
}
