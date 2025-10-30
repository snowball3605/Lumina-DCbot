package Commands

import Util.LanguageManager
import Util.get
import Util.getAll
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.exposed.sql.Database
import java.awt.Color

class Rank: ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "rank") return
        var i = 1
        var string = mutableListOf<String>()
        var message = ""
        val id: List<Long> = getAll("SELECT * FROM `level` ORDER BY `exp` ASC LIMIT 10", "id") as List<Long>
        val exp: List<Long> = getAll("SELECT * FROM `level` ORDER BY `exp` ASC LIMIT 10", "exp") as List<Long>

        while (i <= id.size) {
            string.add("** ${i} **: ${event.jda.getUserById(id[i-1])!!.name}, ${exp[i-1]} exp ")
            i++
        }
        message = string.toString().replace("[", "").replace("]", "")
        event.replyEmbeds(EmbedBuilder().setTitle(LanguageManager.get("Level.message.Info")).setDescription(message).setColor(
            Color.BLUE).build()).queue()
    }

}