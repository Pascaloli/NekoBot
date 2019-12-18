package me.pascal.nekobot

import me.pascal.nekobot.cache.CachingHandler
import me.pascal.nekobot.command.CommandHandler
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.util.stream.Collectors
import javax.security.auth.login.LoginException

class NekoBot {

    private val scanner = Scanner(System.`in`)
    private val databaseFile = File("database.db")
    private val databaseURL = "jdbc:sqlite:${databaseFile.absolutePath}"
    private lateinit var jda: JDA

    companion object {
        lateinit var dbConnection: Connection
        lateinit var commandHandler: CommandHandler
        lateinit var cacheHandler: CachingHandler
    }

    init {
        //Initialise SQLite JDBC Driver
        println("Initialising Database Driver.")
        Class.forName("org.sqlite.JDBC")
        println("Initialised Database Drivers.")

        //Connect to Database
        try {
            println("Connecting to the Database.")
            dbConnection = DriverManager.getConnection(databaseURL)
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
        val statement = dbConnection.createStatement()
        val resultSet = statement.executeQuery(tokenQuery)
        var token = if (resultSet.isAfterLast) { //Empty table
            val token =
                askForToken("No Token set, please enter your Bot Token: ", false)
            val insertTokenQuery = "INSERT INTO config(token) VALUES('$token')"
            dbConnection.createStatement().use {
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
        println("Initialised JDA")

        prepareServersTable()
        commandHandler = CommandHandler()
        cacheHandler = CachingHandler()
        jda.addEventListener(EventListener(jda, dbConnection))
        println("Neko Bot is ready - Remember: The default prefix and suffix is !'")
    }

    private fun prepareDB() {
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
                    "suffix TEXT default '!'\n" +
                    ");"

        dbConnection.createStatement().use {
            it.execute(configTable)
            it.execute(serversTable)
        }
    }

    private fun prepareServersTable() {
        dbConnection.createStatement().use {
            val rs = it.executeQuery("SELECT guildID FROM servers")
            val guilds = this.jda.guilds.stream().collect(Collectors.toList())

            while (rs.next()) {
                val guildID = rs.getString("guildID")
                val guild = guilds.firstOrNull { it.id == guildID }
                if(guild != null) guilds.remove(guild)
            }

            //Guilds the bot is on that arent in the DB
            for(guild in guilds){
                val query = "INSERT INTO servers(guildID) VALUES(${guild.id})"
                it.execute(query)
            }
        }
    }

    private fun askForToken(message: String, updateDB: Boolean): String {
        print(message)
        val token = scanner.nextLine()

        //Update Token in Database
        if (updateDB) {
            val updateTokenQuery = "UPDATE config SET token='$token'"
            dbConnection.createStatement().use {
                it.execute(updateTokenQuery)
            }
        }
        return token
    }

}