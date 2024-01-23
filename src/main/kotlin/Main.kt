import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.db.BpDatabase.BpDatabase
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        db()
    }
}

@OptIn(DelicateCoroutinesApi::class)
// Kotlin Coroutines to call SQLDelight database queries is recommended
fun db() = GlobalScope.launch(Dispatchers.IO) {
    // Initialize the SQLite driver
    val driver = JdbcSqliteDriver("jdbc:sqlite:com.sqldelight.BpDatabase")

    // Execute the SQL command to create the table if it doesn't exist
    driver.execute(
        null, "CREATE TABLE IF NOT EXISTS bp (\n" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " sbp INTEGER,\n" +
                " dbp INTEGER,\n" +
                " event_time TEXT NOT NULL\n" +
                ");", 0
    )

    // Create an instance of the database
    val database = BpDatabase(driver)

    // Get access to the database queries
    val myDatabaseQueries = database.bpDatabaseQueries

    // Insert a new record into the table
    // Pass null for the id since it's auto-incremented
    myDatabaseQueries.InsertBpByValues(null, 120, 80, "today")

    // Print all records from the table
    println(myDatabaseQueries.SelectAll().executeAsList() + "\n")

    // Select a record from the table by id
    println(myDatabaseQueries.SelectBpById(0).executeAsList() + "\n")

    // Delete all records from the table
    myDatabaseQueries.DeleteAllBp()

    // Print all records from the table after deletion
    println(myDatabaseQueries.SelectAll().executeAsList() + "\n")

    // Drop the table
    myDatabaseQueries.DropBpTable()
}