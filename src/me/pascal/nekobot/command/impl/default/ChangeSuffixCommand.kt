package me.pascal.nekobot.command.impl.default

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.PERMISSIONS
import net.dv8tion.jda.api.entities.Message

class ChangeSuffixCommand : Command("suffix", permissions = PERMISSIONS.ADMIN) {

    override fun handle(message: Message) {
        val arguments = message.contentRaw.split(" ", limit = 2)
        if (arguments.size == 1) {
            val oldSuffix = NekoBot.cacheHandler.getCache(message.guild.id).suffix
            if (oldSuffix.isEmpty())
                message.channel.sendMessage("Suffix is currently not set.").queue()
            else
                message.channel.sendMessage("Suffix is currently set to `$oldSuffix`.").queue()

        } else {
            val newSuffix = arguments[1]
            if (newSuffix.contains(" ")) {
                message.channel.sendMessage("Suffix cannot contain a space.").queue()
                return
            } else if (newSuffix == "rem" || newSuffix == "del" || newSuffix == "remove" || newSuffix == "delete") {
                NekoBot.cacheHandler.updateCache(message.guild.id, suffix = "")
                message.channel.sendMessage("Suffix has been removed.").queue()
                return
            } else if (newSuffix.length > 3) {
                message.channel.sendMessage("Suffix cannot be longer than 3 characters.").queue()
                return
            }

            NekoBot.cacheHandler.updateCache(message.guild.id, suffix = newSuffix)
            message.channel.sendMessage("Suffix successfully changed to `$newSuffix`.").queue()
        }
    }

}