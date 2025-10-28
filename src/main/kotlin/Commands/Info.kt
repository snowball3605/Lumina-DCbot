package Commands

import Main.my_obj
import Util.LanguageManager.get
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.lang.String
import kotlin.plus


class Info : ListenerAdapter() {
    public override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.getName().equals("info")) {
            val s = get("Info.message.Created")!!.replace("%created_data%", "24/10/2025")
            val t = get("Info.message.Server_Time")!!.replace("%server_time%", String.valueOf(my_obj))
            event.replyEmbeds(
                EmbedBuilder().setColor(Color.BLUE)
                    .setTitle(get("Info.message") + " " + event.jda.selfUser.name)
                    .setDescription(s + "\n" + t).build()
            ).queue()
            // wtf
        }
    }
}