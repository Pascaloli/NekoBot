package me.pascal.nekobot.command.impl.neko

import me.pascal.nekobot.NekoBot
import me.pascal.nekobot.command.Command
import me.pascal.nekobot.command.LEVEL
import me.pascal.nekobot.command.PERMISSIONS
import me.pascal.nekobot.utils.NekosLifeUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import org.json.JSONObject
import java.util.*

class NekoCommand : Command {

    private var actionMessage: String?
    private var nekoType: TYPE

    constructor(trigger: String, level: LEVEL, type: TYPE) :
            super(trigger, level, PERMISSIONS.USER) {
        this.nekoType = type
        this.actionMessage = null
    }

    constructor(trigger: String, actionMessage: String, level: LEVEL, type: TYPE) :
            super(trigger, level, PERMISSIONS.USER) {
        this.nekoType = type
        this.actionMessage = actionMessage
    }

    fun getNekoType(): TYPE {
        return nekoType
    }

    override fun handle(message: Message) {
        val nsfwCommand = this.level == LEVEL.NSFW
        val nsfwEnabled = NekoBot.cacheHandler.getCache(message.guild.id).nsfw
        val nsfwChannel = message.textChannel.isNSFW
        if (nsfwCommand) {
            if (!nsfwEnabled || !nsfwChannel) {
                message.channel.sendMessage("NSFW is disabled for this ${if (!nsfwEnabled) "server" else "channel"}.")
                        .queue()
                return
            }
        }

        if (this.nekoType == TYPE.ACTION || this.nekoType == TYPE.IMAGE) {
            val imageJSON = NekosLifeUtil.getImage(this.trigger)
            val imageURL = JSONObject(Objects.requireNonNull(imageJSON).toString()).get("url").toString()
            val messageBuilder = MessageBuilder()
            val embed = EmbedBuilder()
            embed.setImage(imageURL)

            if (this.nekoType == TYPE.ACTION) {
                val text = if (message.mentionedMembers.isEmpty()) {
                    "${message.member!!.effectiveName} ${this.actionMessage} himself."
                } else {
                    "${message.mentionedMembers[0]!!.effectiveName} you have been ${this.actionMessage} by ${message.member!!.effectiveName}"
                }
                messageBuilder.setContent(text)
            }
            messageBuilder.setEmbed(embed.build())
            message.channel.sendMessage(messageBuilder.build()).queue()

        } else if (this.nekoType == TYPE.TEXT) {

        }


        super.handle(message)
    }

    enum class TYPE {
        IMAGE, ACTION, TEXT;

        fun getScreenName(): String {
            return this.name
        }
    }

}