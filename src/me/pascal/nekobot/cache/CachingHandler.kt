package me.pascal.nekobot.cache

import me.pascal.nekobot.NekoBot

class CachingHandler {

    private val serverCache = arrayListOf<ServerCache>()

    init {
        NekoBot.dbConnection.createStatement().use {
            val serversQuery = "SELECT * FROM servers"
            val rs = it.executeQuery(serversQuery)
            while(rs.next()){
                val cache = ServerCache(
                    guildID = rs.getString("guildID"),
                    nsfw = rs.getBoolean("nsfw"),
                    prefix = rs.getString("prefix"),
                    suffix = rs.getString("suffix")
                )
                serverCache.add(cache)
            }
        }
    }

    fun updateCache(id: String, nsfw: Boolean? = null, prefix: String? = null, suffix: String? = null) {
        val cache = getCache(id)
        if (nsfw != null) cache.nsfw = nsfw
        if (prefix != null) cache.prefix = prefix
        if (suffix != null) cache.suffix = suffix
    }

    fun getCache(id: String): ServerCache {
        return serverCache.first { it.guildID == id }
    }

}