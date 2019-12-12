import me.pascal.nekobot.EventListener
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import javax.security.auth.login.LoginException


private val scanner = Scanner(System.`in`)
private val databaseFile = File("database.db")
private val databaseURL = "jdbc:sqlite:${databaseFile.absolutePath}"
private lateinit var jda: JDA
private lateinit var dbconnection: Connection

fun main() {

    //Initialise SQLite JDBC Driver
    println("Initialising Database Driver.")
    Class.forName("org.sqlite.JDBC")
    println("Initialised Database Drivers.")

    //Connect to Database
    try {
        println("Connecting to the Database.")
        dbconnection = DriverManager.getConnection(databaseURL)
        println("Connected to the Database.")
    } catch (e: SQLException) {
        println(e.message)
    }

    //Prepare Database
    println("Preparing Database")
    prepareDB()
    println("Prepared Database.")

    //Get Bot Token
    val tokenQuery = "SELECT token FROM config"
    val statement = dbconnection.createStatement()
    val resultSet = statement.executeQuery(tokenQuery)
    var token = if (resultSet.isAfterLast) { //Empty table
        val token = askForToken("No Token set, please enter your Bot Token: ", false)
        val insertTokenQuery = "INSERT INTO config(token) VALUES('$token')"
        dbconnection.createStatement().use {
            it.execute(insertTokenQuery)
        }
        token
    } else {
        resultSet.next()
        resultSet.getString("token")!!
    }

    //Init JDA using token from DB, ask for new one if invalid
    println("Initialising JDA")
    do {
        try {
            jda =
                JDABuilder(AccountType.BOT).setToken(token).build().awaitReady()
        } catch (ex: LoginException) {
            token = askForToken("Invalid Token, please enter a new one: ", true)
        }
    } while (!::jda.isInitialized)
    //wohoo JDA is now initialised

    jda.addEventListener(EventListener(jda, dbconnection))
    println("Initialised JDA")
}

fun prepareDB() {
    val configTable =
        "CREATE TABLE IF NOT EXISTS config \n" +
                "( \n" +
                "token TEXT \n" +
                ");"
    val serversTable =
        "CREATE TABLE IF NOT EXISTS servers \n" +
                "( \n" +
                "guildID TEXT, \n" +
                "nsfw NUMERIC default 0, \n" +
                "prefix TEXT default '!', \n" +
                "suffix TEXT \n" +
                ");"

    dbconnection.createStatement().use {
        it.execute(configTable)
        it.execute(serversTable)
    }
}

fun askForToken(message: String, updateDB: Boolean): String {
    print(message)
    val token = scanner.nextLine()

    //Update Token in Database
    if (updateDB) {
        val updateTokenQuery = "UPDATE config SET token='$token'"
        dbconnection.createStatement().use {
            it.execute(updateTokenQuery)
        }
    }
    return token
}