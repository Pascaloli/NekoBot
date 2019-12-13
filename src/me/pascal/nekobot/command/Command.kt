package me.pascal.nekobot.command

import me.pascal.nekobot.NekoBot
import net.dv8tion.jda.api.entities.Message

open class Command(var trigger: String, val level: LEVEL, val permissions: PERMISSIONS){
    val dbConnection = NekoBot.dbConnection
    open fun handle(message: Message){
    }
}

enum class LEVEL {
    NSFW, SFW, NONE
}

enum class PERMISSIONS {
    USER, ADMIN
}