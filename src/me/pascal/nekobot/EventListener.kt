package me.pascal.nekobot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.sql.Connection

class EventListener(val jda: JDA, val dbConnection: Connection) : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        val query = "INSERT INTO servers(guildID) VALUES(${event.guild.id})"
        dbConnection.createStatement().use {
            it.execute(query)
        }
        super.onGuildJoin(event)
    }

    override fun onReady(event: ReadyEvent) {
        for (guild in event.jda.guilds) {

        }

        super.onReady(event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val message = event.message
        if (message.author.id == message.jda.selfUser.id) return
        val possibleCommand = message.contentRaw.split(" ")[0]
        val cache = NekoBot.cacheHandler.getCache(message.guild.id)
        val prefix = cache.prefix
        val suffix = cache.suffix

        if (possibleCommand.startsWith(prefix) && possibleCommand.endsWith(suffix)) {
            val actualCommand =
                possibleCommand.substring(prefix.length, possibleCommand.length - suffix.length)

            val command = NekoBot.commandHandler.getCommand(actualCommand)
            if (command != null)
                NekoBot.commandHandler.handle(message, command)
        }

        super.onMessageReceived(event)
    }
}