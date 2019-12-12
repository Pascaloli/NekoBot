package me.pascal.nekobot

import net.dv8tion.jda.api.JDA
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

    override fun onMessageReceived(event: MessageReceivedEvent) {

        super.onMessageReceived(event)
    }
}