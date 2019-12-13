package me.pascal.nekobot.command.commands

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.LEVEL
import me.pascal.nekobot.command.PERMISSIONS
import net.dv8tion.jda.api.entities.Message

class ToggleNSFWCommand : Command("nsfw", LEVEL.NONE, PERMISSIONS.ADMIN) {

    override fun handle(message: Message) {
        val oldNSFW = NekoBot.cacheHandler.getCache(message.guild.id).nsfw
        val updateNSFWQuery = "UPDATE servers SET nsfw = ? WHERE guildID = ${message.guild.id}"
        val prepStatement = dbConnection.prepareStatement(updateNSFWQuery)
        prepStatement.setBoolean(1, !oldNSFW)
        prepStatement.execute()

        NekoBot.cacheHandler.updateCache(message.guild.id, nsfw = !oldNSFW)
        message.channel.sendMessage("NSFW successfully turned `${if(!oldNSFW) "on" else "off"}`.").queue()
        super.handle(message)
    }

}