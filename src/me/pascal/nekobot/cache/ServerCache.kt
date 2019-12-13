package me.pascal.nekobot.cache

data class ServerCache(var guildID: String, var nsfw: Boolean = false, var prefix: String = "!", var suffix: String = "!")