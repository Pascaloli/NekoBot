package me.pascal.nekobot.command

data class Command(var trigger: String, val level: LEVEL, val type: TYPE)

enum class LEVEL {
    NSFW, SFW, NONE
}

enum class TYPE {
    ACTION, IMAGE, TEXT, ADMIN
}