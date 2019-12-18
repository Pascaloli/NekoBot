package me.pascal.nekobot.command

import me.pascal.nekobot.command.impl.default.ChangePrefixCommand
import me.pascal.nekobot.command.impl.default.ChangeSuffixCommand
import me.pascal.nekobot.command.impl.default.HelpCommand
import me.pascal.nekobot.command.impl.default.ToggleNSFWCommand
import me.pascal.nekobot.command.impl.neko.NekoCommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message

class CommandHandler {

    private val commands = arrayListOf(

            ChangePrefixCommand(),
            ChangeSuffixCommand(),
            ToggleNSFWCommand(),
            HelpCommand(),

            NekoCommand("tickle", "tickled", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("poke", "poked", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("kiss", "kissed", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("slap", "slapped", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("hug", "hugged", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("pat", "patted", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("feed", "fed", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("cuddle", "cuddled", LEVEL.SFW, NekoCommand.TYPE.ACTION),
            NekoCommand("spank", "spanked", LEVEL.NSFW, NekoCommand.TYPE.ACTION),


            NekoCommand("meow", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("8ball", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("lizard", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("goose", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("avatar", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("fox_girl", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("gecg", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("smug", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("kemonomimi", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("holo", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("woof", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("baka", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("neko", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("gasm", LEVEL.SFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("waifu", LEVEL.SFW, NekoCommand.TYPE.IMAGE),

            NekoCommand("femdom", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("classic", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("ngif", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("erofeet", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("erok", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("les", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("hololewd", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("lewdk", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("keta", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("feetg", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("nsfw_neko_gif", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("eroyuri", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("kuni", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("tits", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("pussy_jpg", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("cum_jpg", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("pussy", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("lewdkemo", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("lewd", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("cum", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("Random_hentai_gif", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("nsfw_avatar", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("boobs", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("feet", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("solog", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("wallpaper", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("bj", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("yuri", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("trap", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("anal", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("blowjob", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("holoero", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("hentai", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("futanari", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("ero", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("solo", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("pwankg", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("eron", LEVEL.NSFW, NekoCommand.TYPE.IMAGE),
            NekoCommand("erokemo", LEVEL.NSFW, NekoCommand.TYPE.IMAGE)
    )

    fun getCommand(name: String): Command? {
        return commands.firstOrNull { it.trigger.equals(name, ignoreCase = true) }
    }

    fun getCommands(): ArrayList<Command> {
        return commands
    }

    fun handle(message: Message, command: Command) {
        if (command.permissions == PERMISSIONS.ADMIN) {
            val author = message.member!!
            //TODO: REMOVE HARDCODED ID (DEBUG PURPOSES)
            if (author.hasPermission(Permission.ADMINISTRATOR) || author.user.id == "526189924559618089") {
                command.handle(message)
            } else {
                message.channel.sendMessage("You can't use this command.").queue()
            }

        } else {
            command.handle(message)
        }
    }
}