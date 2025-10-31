package com.onmi_tech

import Plugin
import com.onmi_tech.LoggerManager.status
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okio.FileNotFoundException
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import kotlin.math.E


var lang: MutableMap<String?, Any?>? = null
var Data: MutableMap<String?, Any?>? = null
class Main : Plugin, ListenerAdapter() {
    override val name = "Info"

    override fun onEnable(jda: JDA, data: MutableMap<String?, Any?>?) {
        val folder = File("plugins/Info/lang")
        val en_UK = File(folder, "en_UK.yml")
        folder.mkdirs()
        if (!en_UK.exists()) {
            val inputStream = object {}.javaClass.getResourceAsStream("/lang/en_UK.yml")
                ?: throw FileNotFoundException("Could not find en_UK.yml in resources")

            inputStream.use { input ->
                en_UK.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        val folder_ = File("plugins/Info")
        val setting = File(folder_,"SETTING.yml")
        if (!setting.exists()) {
            val inputStream = object {}.javaClass.getResourceAsStream("/SETTING.yml")
            ?: throw FileNotFoundException("Could not find setting.yml in resources")
            inputStream.use { input ->
                setting.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        jda.addEventListener(this)
        Files.newInputStream(Paths.get("plugins/Info/SETTING.yml")).use { `in` ->
            val yaml: Yaml = Yaml()
            Data = yaml.load(`in`)
        }
        Files.newInputStream(Paths.get("plugins/Info/lang/" + Data!!.get("lang") + ".yml")).use { `in` ->
            val yaml: Yaml = Yaml()
            lang = yaml.load(`in`)
        }
    }

    override fun onDisable() {
        TODO("Not yet implemented")
    }

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name != "info") return

        val message: String = lang!!.get("Info.message.Created").toString().replace("%created_data%", "2025/10/24")
        event.replyEmbeds(EmbedBuilder().setTitle(lang!!.get("Info.message").toString()).setDescription(message +  "\n" + lang!!.get("Info.message.Server_Time").toString().replace("%server_time%",
            LocalDateTime.now().toString())).build()).queue()
        status(lang!!.get("slash_command_use").toString().replace("%name%", event.name), LogLevel.INFO)

    }
}