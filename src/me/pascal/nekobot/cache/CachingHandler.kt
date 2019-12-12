package me.pascal.nekobot.cache

class CachingHandler {

    private val serverCache = arrayListOf<ServerCache>()

    fun overrideCache(id: String, nsfw: Boolean? = null, prefix: String? = null, suffix: String? = null) {
        val cache = getCache(id)
        if (nsfw != null) cache.nsfw = nsfw
        if (prefix != null) cache.prefix = prefix
        if (suffix != null) cache.suffix = suffix
    }

    fun addCache(cache: ServerCache) {
        serverCache.add(cache)
    }

    fun getCache(id: String): ServerCache {
        return serverCache.first { it.guildID == id }
    }

}