package me.pascal.nekobot.command.commands

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.LEVEL
import me.pascal.nekobot.command.PERMISSIONS
import net.dv8tion.jda.api.entities.Message

class ChangePrefixCommand : Command("prefix", LEVEL.NONE, PERMISSIONS.ADMIN) {

    override fun handle(message: Message) {
        val arguments = message.contentRaw.split(" ", limit = 2)
        if (arguments.size == 1) {
            val oldPrefix = NekoBot.cacheHandler.getCache(message.guild.id).prefix
            if (oldPrefix.isEmpty())
                message.channel.sendMessage("Prefix is currently not set.").queue()
            else
                message.channel.sendMessage("Prefix is currently set to `$oldPrefix`.").queue()

        } else {
            val newPrefix = arguments[1]
            if (newPrefix.contains(" ")) {
                message.channel.sendMessage("Prefix cannot contain a space.").queue()
                return
            } else if (newPrefix == "rem" || newPrefix == "del" || newPrefix == "remove" || newPrefix == "delete") {
                val query = "UPDATE servers SET prefix = '' WHERE guildID = ${message.guild.id}"
                dbConnection.createStatement().use {
                    it.execute(query)
                    NekoBot.cacheHandler.updateCache(message.guild.id, prefix = "")
                    message.channel.sendMessage("Prefix has been removed.").queue()
                }
                return
            } else if (newPrefix.length > 3) {
                message.channel.sendMessage("Prefix cannot be longer than 3 characters.").queue()
                return
            }

            val query = "UPDATE servers SET prefix = ? WHERE guildID = ${message.guild.id}"
            val prepStatement = dbConnection.prepareStatement(query)
            prepStatement.setString(1, newPrefix)
            prepStatement.execute()
            NekoBot.cacheHandler.updateCache(message.guild.id, prefix = newPrefix)
            message.channel.sendMessage("Prefix successfully changed to `$newPrefix`.").queue()
        }
    }

}