package me.pascal.nekobot.command.impl.default

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.LEVEL
import me.pascal.nekobot.command.impl.neko.NekoCommand
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message

class HelpCommand : Command("help") {

    override fun handle(message: Message) {

        val embed = EmbedBuilder()
        embed.setTitle("Help Overview")
        embed.setDescription(getDescription(message.guild.id))

        embed.addField("Neko Commands", getNekos(), false)

        message.channel.sendMessage(embed.build()).queue()
        super.handle(message)
    }

    private fun getDescription(guildID: String): String {
        var temp = ""
        val cache = NekoBot.cacheHandler.getCache(guildID)
        if (cache.prefix.isNotEmpty()) temp += "Prefix: `${cache.prefix}`\n"
        if (cache.suffix.isNotEmpty()) temp += "Suffix: `${cache.suffix}`\n"
        temp += "Example: `${cache.prefix}neko${cache.suffix}`\n"
        temp += "NSFW Commands are *italic*"
        return temp
    }

    private fun getNekos(): String {
        val nekoCommands = NekoBot.commandHandler.getCommands().filterIsInstance<NekoCommand>()
        var temp = "**Actions**: "
        temp += nekoCommands.filter { it.getNekoType() == NekoCommand.TYPE.ACTION }.joinToString { if (it.level == LEVEL.NSFW) "*${it.trigger}*" else it.trigger }
        temp += "\n**Images**: "
        temp += nekoCommands.filter { it.getNekoType() == NekoCommand.TYPE.IMAGE }.joinToString { if (it.level == LEVEL.NSFW) "*${it.trigger}*" else it.trigger }
        return temp
    }

}