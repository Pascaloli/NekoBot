package me.pascal.nekobot.utils

import java.net.URL

object NekoUtil {

    fun getImage(endpoint: String): String {
        val url = "https://nekos.life/api/v2/img/$endpoint"
        val openConnection = URL(url).openConnection()
        openConnection.addRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0"
        )
        openConnection.getInputStream().bufferedReader().use {
            return it.readLine()
        }
    }

}