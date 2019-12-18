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
        return NekoBot.commandHandler.getCommands()
                .asSequence()
                .filterIsInstance<NekoCommand>()
                .map { it.getNekoType() to (if (it.level == LEVEL.NSFW) "*${it.trigger}*" else it.trigger) }
                .groupBy { it.first }
                .map { (type, triggers) -> type to triggers }
                .joinToString("\n") { (type, triggers) -> "**${type.name}**: ${triggers.joinToString { it.second }}" }
    }

}