import assignments.assignment01.*

fun main() {

    println("======================================================")
    println("       ADVANCED LIBRARY MANAGEMENT SYSTEM")
    println("======================================================")
    println("Welcome to the Library Management System!")

    val library = Library()

    println("\n--- Registering Library Staff ---")

    val alice = LibraryStaff(
        name = "Alice",
        role = "Librarian",
        library = library
    )

    val bob = LibraryStaff(
        name = "Bob",
        role = "Assistant Librarian",
        library = library
    )

    library.registerStaff(alice)
    library.registerStaff(bob)

    println("Registered: ${alice.name} (${alice.role})")
    println("Registered: ${bob.name} (${bob.role})")

    println("\n--- Registering Library Users ---")

    val john = LibraryUser(name = "John")
    val sarah = PremiumUser(name = "Sarah")

    library.registerUser(john)
    library.registerUser(sarah)

    println("Registered: ${john.name} (Regular User)")
    println("Borrow limit: ${john.borrowLimit}")

    println("Registered: ${sarah.name} (Premium User)")
    println("Borrow limit: ${sarah.borrowLimit}")

    println("\n--- Creating Library Items ---")

    val book = Book(
        author = "George Orwell",
        id = 101,
        title = "1984"
    )

    val magazine = Magazine(
        issueNumber = 34,
        id = 102,
        title = "Tech Weekly"
    )

    val digitalMedia = DigitalMedia(
        fileSize = 12.5,
        format = "PDF",
        id = 103,
        title = "Kotlin Basics"
    )

    println("Book: ${book.title} by ${book.author}")
    println("Magazine: ${magazine.title}, Issue ${magazine.issueNumber}")
    println("Digital Media: ${digitalMedia.title} (${digitalMedia.format})")

    println("\n--- Adding Items to the Library ---")

    alice.addItem(book)
    alice.addItem(magazine)
    bob.addItem(digitalMedia)

    println("\n--- Initial Library Catalog ---")
    library.displayAllItems()

    println("\n--- Test 1: John Borrows a Book ---")

    john.borrowItem(book, "2025-01-01")

    println("Book available: ${book.isAvailable}")
    println("Borrowed by: ${book.borrowedBy}")
    println("Due date: ${book.dueDate}")

    println("\n--- Test 2: Sarah Borrows a Magazine ---")

    sarah.borrowItem(magazine, "2025-01-01")

    println("Magazine available: ${magazine.isAvailable}")
    println("Borrowed by: ${magazine.borrowedBy}")
    println("Due date: ${magazine.dueDate}")

    println("\n--- Test 3: Borrowing an Unavailable Item ---")

    john.borrowItem(magazine, "2025-01-02")

    println("Magazine still borrowed by: ${magazine.borrowedBy}")

    println("\n--- Test 4: Check Overdue Items ---")

    val johnIsOverdue = john.isOverdue("2025-01-10")

    println("Does John have overdue items? $johnIsOverdue")

    val extraBook = Book(
        author = "Robert C. Martin",
        id = 105,
        title = "Clean Code"
    )

    alice.addItem(extraBook)

    println("\nJohn attempts another borrowing while overdue:")
    john.borrowItem(extraBook, "2025-01-10")

    println("\n--- Test 5: John Returns His Book ---")

    john.returnItem(book, "2025-01-10")

    println("Book available: ${book.isAvailable}")
    println("Borrowed by: ${book.borrowedBy}")
    println("Due date: ${book.dueDate}")

    println("John overdue? ${john.isOverdue("2025-01-10")}")

    println("\n--- Test 6: Borrow After Returning ---")

    john.borrowItem(extraBook, "2025-01-10")

    println("\n--- Test 7: Sarah Returns a Magazine Late ---")

    sarah.returnItem(magazine, "2025-01-10")

    println("\n--- Test 8: Access Digital Media ---")

    john.borrowItem(digitalMedia, "2025-01-10")

    println("Digital media available: ${digitalMedia.isAvailable}")
    println("Borrowed by: ${digitalMedia.borrowedBy}")
    println("Due date: ${digitalMedia.dueDate}")

    println("\n--- Test 9: Premium User Borrowing ---")

    sarah.borrowItem(book, "2025-01-11")

    println("Premium borrowing due date: ${book.dueDate}")

    println("\n--- Test 10: Administrative Actions ---")

    val newBook = Book(
        author = "Aldous Huxley",
        id = 104,
        title = "Brave New World"
    )

    alice.addItem(newBook)

    bob.removeItem(101)

    alice.removeItem(102)

    println("\n--- Test 11: Search Library Items ---")

    val searchResults = library.searchByTitle("Brave")

    println("Search results for 'Brave':")
    searchResults.forEach { item ->
        println("${item.id}: ${item.title}")
    }

    val foundItem = library.searchById(103)

    println("Search by ID 103: ${foundItem?.title ?: "Not found"}")

    println("\n--- Test 12: User Borrowing Records ---")

    library.viewBorrowedItems(john)
    library.viewBorrowedItems(sarah)

    println("\n--- Final Library Catalog ---")

    library.displayAllItems()

    println("\n--- Administrative Action Logs ---")

    library.listAdminActions()

    println("\n======================================================")
    println("       LIBRARY MANAGEMENT SYSTEM COMPLETE")
    println("======================================================")
}
