package me.pascal.nekobot.command.impl.default

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.PERMISSIONS
import net.dv8tion.jda.api.entities.Message

class ToggleNSFWCommand : Command("nsfw", permissions = PERMISSIONS.ADMIN) {

    override fun handle(message: Message) {
        val oldNSFW = NekoBot.cacheHandler.getCache(message.guild.id).nsfw
        NekoBot.cacheHandler.updateCache(message.guild.id, nsfw = !oldNSFW)
        message.channel.sendMessage("NSFW successfully turned `${if (!oldNSFW) "on" else "off"}`.").queue()
        super.handle(message)
    }

}