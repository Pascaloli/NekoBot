package me.pascal.nekobot.command

import net.dv8tion.jda.api.entities.Message

class CommandHandler {

    val commands = arrayListOf<Command>(
        Command("femdom", LEVEL.NSFW, TYPE.IMAGE),
        Command("tickle", LEVEL.SFW, TYPE.IMAGE),
        Command("classic", LEVEL.NSFW, TYPE.IMAGE),
        Command("ngif", LEVEL.NSFW, TYPE.IMAGE),
        Command("erofeet", LEVEL.NSFW, TYPE.IMAGE),
        Command("meow", LEVEL.SFW, TYPE.IMAGE),
        Command("erok", LEVEL.NSFW, TYPE.IMAGE),
        Command("poke", LEVEL.SFW, TYPE.IMAGE),
        Command("les", LEVEL.NSFW, TYPE.IMAGE),
        Command("hololewd", LEVEL.NSFW, TYPE.IMAGE),

        //TODO: Categorise stuff
        Command("lewdk", LEVEL.SFW, TYPE.IMAGE),
        Command("keta", LEVEL.SFW, TYPE.IMAGE),
        Command("feetg", LEVEL.SFW, TYPE.IMAGE),
        Command("nsfw_neko_gif", LEVEL.SFW, TYPE.IMAGE),
        Command("eroyuri", LEVEL.SFW, TYPE.IMAGE),
        Command("kiss", LEVEL.SFW, TYPE.IMAGE),
        Command("8ball", LEVEL.SFW, TYPE.IMAGE),
        Command("kuni", LEVEL.SFW, TYPE.IMAGE),
        Command("tits", LEVEL.SFW, TYPE.IMAGE),
        Command("pussy_jpg", LEVEL.SFW, TYPE.IMAGE),
        Command("cum_jpg", LEVEL.SFW, TYPE.IMAGE),
        Command("pussy", LEVEL.SFW, TYPE.IMAGE),
        Command("lewdkemo", LEVEL.SFW, TYPE.IMAGE),
        Command("lizard", LEVEL.SFW, TYPE.IMAGE),
        Command("slap", LEVEL.SFW, TYPE.IMAGE),
        Command("lewd", LEVEL.SFW, TYPE.IMAGE),
        Command("cum", LEVEL.SFW, TYPE.IMAGE),
        Command("cuddle", LEVEL.SFW, TYPE.IMAGE),
        Command("spank", LEVEL.SFW, TYPE.IMAGE),
        Command("smallboobs", LEVEL.SFW, TYPE.IMAGE),
        Command("goose", LEVEL.SFW, TYPE.IMAGE),
        Command("Random_hentai_gif", LEVEL.SFW, TYPE.IMAGE),
        Command("avatar", LEVEL.SFW, TYPE.IMAGE),
        Command("fox_girl", LEVEL.SFW, TYPE.IMAGE),
        Command("nsfw_avatar", LEVEL.SFW, TYPE.IMAGE),
        Command("hug", LEVEL.SFW, TYPE.IMAGE),
        Command("gecg", LEVEL.SFW, TYPE.IMAGE),
        Command("boobs", LEVEL.SFW, TYPE.IMAGE),
        Command("pat", LEVEL.SFW, TYPE.IMAGE),
        Command("feet", LEVEL.SFW, TYPE.IMAGE),
        Command("smug", LEVEL.SFW, TYPE.IMAGE),
        Command("kemonomimi", LEVEL.SFW, TYPE.IMAGE),
        Command("solog", LEVEL.SFW, TYPE.IMAGE),
        Command("holo", LEVEL.SFW, TYPE.IMAGE),
        Command("wallpaper", LEVEL.SFW, TYPE.IMAGE),
        Command("bj", LEVEL.SFW, TYPE.IMAGE),
        Command("woof", LEVEL.SFW, TYPE.IMAGE),
        Command("yuri", LEVEL.SFW, TYPE.IMAGE),
        Command("trap", LEVEL.SFW, TYPE.IMAGE),
        Command("anal", LEVEL.SFW, TYPE.IMAGE),
        Command("baka", LEVEL.SFW, TYPE.IMAGE),
        Command("blowjob", LEVEL.SFW, TYPE.IMAGE),
        Command("holoero", LEVEL.SFW, TYPE.IMAGE),
        Command("feed", LEVEL.SFW, TYPE.IMAGE),
        Command("neko", LEVEL.SFW, TYPE.IMAGE),
        Command("gasm", LEVEL.SFW, TYPE.IMAGE),
        Command("hentai", LEVEL.SFW, TYPE.IMAGE),
        Command("futanari", LEVEL.SFW, TYPE.IMAGE),
        Command("ero", LEVEL.SFW, TYPE.IMAGE),
        Command("solo", LEVEL.SFW, TYPE.IMAGE),
        Command("waifu", LEVEL.SFW, TYPE.IMAGE),
        Command("pwankg", LEVEL.SFW, TYPE.IMAGE),
        Command("eron", LEVEL.SFW, TYPE.IMAGE),
        Command("erokemo", LEVEL.SFW, TYPE.IMAGE)
    )

    fun handle(message: Message) {

    }
}