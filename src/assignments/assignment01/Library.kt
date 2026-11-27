package assignments.assignment01


/**
 * Central class responsible for managing the library.
 *
 * Maintains:
 * - Library items
 * - Registered users
 * - Registered staff
 *
 * Provides catalog searches, item listings,
 * borrowing reports, and administrative logs.
 */
class Library {

    // Internal mutable collections managed by this class.
    private val catalog = mutableListOf<LibraryItem>()
    private val registeredStaff = mutableListOf<LibraryStaff>()
    private val registeredUsers = mutableListOf<LibraryUser>()

    // Public read-only snapshots of the collections.
    val items: List<LibraryItem>
        get() = catalog.toList()

    val staff: List<LibraryStaff>
        get() = registeredStaff.toList()

    val users: List<LibraryUser>
        get() = registeredUsers.toList()

    /**
     * Registers a staff member with the library.
     */
    fun registerStaff(employee: LibraryStaff) {
        if (employee !in registeredStaff) {
            registeredStaff.add(employee)
        }
    }

    /**
     * Registers a library user.
     */
    fun registerUser(user: LibraryUser) {
        if (user !in registeredUsers) {
            registeredUsers.add(user)
        }
    }

    /**
     * Adds an item to the shared catalog.
     *
     * Internal visibility keeps this operation out of
     * the public API intended for ordinary library users.
     *
     * Returns false if an item with the same ID exists.
     */
    internal fun addCatalogItem(item: LibraryItem): Boolean {

        if (catalog.any { it.id == item.id }) {
            return false
        }

        catalog.add(item)
        return true
    }

    /**
     * Removes an item by its ID.
     *
     * Borrowed items cannot be removed.
     *
     * Returns true if an eligible item was removed.
     */
    internal fun removeCatalogItem(itemId: Int): Boolean {

        return catalog.removeIf {
            it.id == itemId && it.isAvailable
        }
    }

    /**
     * Displays all library items and their current status.
     */
    fun displayAllItems() {

        if (catalog.isEmpty()) {
            println("No library items available.")
            return
        }

        catalog.forEach { item ->

            // Determine the item-specific description.
            val description = when (item) {

                is Book ->
                    "Book by ${item.author}"

                is Magazine ->
                    "Magazine, Issue ${item.issueNumber}"

                is DigitalMedia ->
                    "Digital Media (${item.format}, ${item.fileSize} MB)"

                else -> "Library Item"
            }

            println("ID: ${item.id} | ${item.title} | $description")

            println(
                "Available: ${item.isAvailable} | " +
                        "Borrowed by: ${item.borrowedBy ?: "Nobody"} | " +
                        "Due: ${item.dueDate ?: "N/A"}"
            )
        }
    }

    /**
     * Searches for library items containing the supplied title.
     *
     * Searches are case-insensitive.
     */
    fun searchByTitle(query: String): List<LibraryItem> {

        return catalog.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    /**
     * Searches for a specific item using its ID.
     *
     * Returns null if no matching item exists.
     */
    fun searchById(itemId: Int): LibraryItem? {

        return catalog.find {
            it.id == itemId
        }
    }

    /**
     * Displays all items borrowed by a registered user.
     */
    fun viewBorrowedItems(user: LibraryUser) {

        if (user !in registeredUsers) {
            println("User ${user.name} is not registered.")
            return
        }

        if (user.borrowedItems.isEmpty()) {
            println("${user.name} has no borrowed items.")
            return
        }

        println("Items borrowed by ${user.name}:")

        user.borrowedItems.forEach { item ->
            println(
                "${item.title} (due: ${item.dueDate ?: "N/A"})"
            )
        }
    }

    /**
     * Displays all administrative action logs recorded
     * by the library's registered staff members.
     */
    fun listAdminActions() {

        val entries = registeredStaff
            .flatMap { it.actionLogs }
            .sorted()

        if (entries.isEmpty()) {
            println("No administrative actions recorded.")
            return
        }

        entries.forEach { entry ->
            println(entry)
        }
    }
}
