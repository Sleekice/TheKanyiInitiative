package assignments.assignment01

/**
 * Contract for administrative actions.
 *
 * Any class implementing this interface must provide
 * implementations for adding and removing library items.
 */
interface AdminActions {

    // Add an item to the library catalog.
    fun addItem(item: LibraryItem)

    // Remove an existing item using its unique ID.
    fun removeItem(itemId: Int)
}
