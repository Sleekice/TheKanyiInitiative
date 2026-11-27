package assignments.assignment01

abstract class LibraryItem(
    val id: Int,
    val title: String,
    var isAvailable: Boolean = true,
    var borrowedBy: String? = null,
    var dueDate: String? = null
) {

    // Each subclass must define its own borrowing behavior.
    abstract fun borrowItem(
        userName: String,
        currentDate: String
    )


    fun returnItem() {
        isAvailable = true
        borrowedBy = null
        dueDate = null //Not in requirement, but added.
    }


}