package me.pascal.nekobot.cache

import me.pascal.nekobot.NekoBot
import net.dv8tion.jda.api.entities.Guild

class CachingHandler {

    private val serverCache = arrayListOf<ServerCache>()

    init {
        NekoBot.dbConnection.createStatement().use {
            val serversQuery = "SELECT * FROM servers"
            val rs = it.executeQuery(serversQuery)
            while (rs.next()) {
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

    fun updateCache(id: String, nsfw: Boolean? = null, prefix: String? = null, suffix: String? = null, updateDB: Boolean = true) {
        val cache = getCache(id)

        if (nsfw != null) cache.nsfw = nsfw
        if (prefix != null) cache.prefix = prefix
        if (suffix != null) cache.suffix = suffix

        if (updateDB) {
            val query = "UPDATE servers SET nsfw = ?, prefix = ?, suffix = ? WHERE guildID = $id"
            val prepStatement = NekoBot.dbConnection.prepareStatement(query)
            prepStatement.setBoolean(1, cache.nsfw)
            prepStatement.setString(2, cache.prefix)
            prepStatement.setString(3, cache.suffix)
            prepStatement.execute()
        }
    }

    fun createCache(guild: Guild) {
        val query = "INSERT INTO servers(guildID) VALUES(${guild.id})"
        NekoBot.dbConnection.createStatement().use {
            it.execute(query)
        }

        val cache = ServerCache(guild.id)
        this.serverCache.add(cache)
    }

    fun getCache(id: String): ServerCache {
        return serverCache.first { it.guildID == id }
    }

    fun hasCache(id: String): Boolean {
        return serverCache.firstOrNull { it.guildID == id } != null
    }

}