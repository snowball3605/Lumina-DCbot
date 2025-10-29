package Event

import Util.LanguageManager
import Util.get
import Util.getLevel
import Util.levelup
import Util.write
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Experience_SendMessage: ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.author.isBot) return
        if (event.guild.idLong != Main.data?.get("Owner_Discord_Server_ID")) return

        if (Main.data!!.get("Upgrade_Message_Channel") == null) return

        if (get("level", "exp", "id", event.member?.idLong.toString()) == null) {
            write("INSERT INTO `level` (id, exp) VALUES (${event.member?.idLong.toString()}, 5)")
        } else {
            val exp_old = Integer.parseInt(get("level", "exp", "id", event.member?.idLong.toString()) as String?)
            write("UPDATE `level` SET `exp` = ${exp_old + 5} WHERE `id` = ${event.member?.idLong}")
        }

        val xp = get("level", "exp", "id", event.member?.idLong.toString())

        if (levelup(Integer.parseInt(xp as String?).toDouble())) {
            event.jda.getTextChannelById(Main.data!!.get("Upgrade_Message_Channel") as Long)?.sendMessage(event.member!!.user.asMention+ " " + LanguageManager.get("Level.upgrade.success")!!.replace("%level%",
                getLevel(Integer.parseInt(xp as String?).toDouble()).toString()
            ))!!.queue()
        }
    }
}