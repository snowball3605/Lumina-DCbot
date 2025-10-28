package Commands

import Main
import Util.LanguageManager.get
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


class Reload : ListenerAdapter() {
    public override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.getName().equals("reload")) {
            var t = true
            val s = Main.data!!.get("Owner_Discord_ID") as MutableList<Long?>

            for (id in s) {
                if (event.member?.user?.idLong?.equals(id) == true) {
                    try {
                        reload()
                        event.reply(get("Reload.success").toString()).queue()
                        t = false
                        break
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
            }
            if (t) {
                event.reply(get("Reload.no_permission").toString()).queue()
            }
        }
    }

    @Throws(IOException::class)
    fun reload() {
        val `in` = Files.newInputStream(Paths.get("SETTING.yml"))
        val yaml: Yaml = Yaml()
        Main.data = yaml.load(`in`)
        Main.language = Main.data!!.get("lang").toString()
    }
}