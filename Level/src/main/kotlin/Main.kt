package com.onmi_tech

import Plugin
import Util.get
import Util.getAll
import Util.getLevel
import Util.init
import Util.levelup
import Util.write
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.yaml.snakeyaml.Yaml
import java.awt.Color
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager

var database_link = "jdbc:sqlite:plugins/Level/data/exp.db"
var Connection: Connection? = null
var Data: MutableMap<String?, Any?>? = null
var language: MutableMap<String?, String?>? = null

class Main: Plugin, ListenerAdapter() {
    override val name = "Level"

    override fun onEnable(
        jda: JDA,
        data: MutableMap<String?, Any?>?
    ) {
        Class.forName("org.sqlite.JDBC")
        val folder_ = File("plugins/Level")
        val folder__ = File("plugins/Level/data")
        folder__.mkdirs()
        val setting = File(folder_,"SETTING.yml")
        folder_.mkdirs()
        if (!setting.exists()) {
            val inputStream = object {}.javaClass.getResourceAsStream("/SETTING.yml")
                ?: throw FileNotFoundException("Could not find setting.yml in resources")
            inputStream.use { input ->
                setting.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        val folder = File("plugins/Level/lang")
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

        Files.newInputStream(Paths.get("plugins/Level/SETTING.yml")).use { `in` ->
            val yaml: Yaml = Yaml()
            Data = yaml.load(`in`)
        }

        Files.newInputStream(Paths.get("plugins/Level/lang/en_UK.yml")).use { `in` ->
            val yaml: Yaml = Yaml()
            language = yaml.load(`in`)
        }
        Connection = DriverManager.getConnection(database_link)
        init()
        jda.addEventListener(this)
        val commands: CommandListUpdateAction = jda.updateCommands()
        commands.addCommands(
            Commands.slash("info", "info")
        )
    }

    override fun onDisable() {

    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.author.isBot) return
        if (event.guild.idLong != Data?.get("Owner_Discord_Server_ID")) return
        if (Data!!.get("Upgrade_Message_Channel") == null) return

        if (get("level", "exp", "id", event.member?.idLong.toString()) == null) {
            write("INSERT INTO `level` (id, exp) VALUES (${event.member?.idLong.toString()}, 5)")
        } else {
            val exp_old = Integer.parseInt(get("level", "exp", "id", event.member?.idLong.toString()) as String?)
            write("UPDATE `level` SET `exp` = ${exp_old + 5} WHERE `id` = ${event.member?.idLong}")
        }

        val xp = get("level", "exp", "id", event.member?.idLong.toString())

        if (levelup(Integer.parseInt(xp as String?).toDouble())) {
            event.jda.getTextChannelById(Data!!.get("Upgrade_Message_Channel") as Long)?.sendMessage(event.member!!.user.asMention+ " " + language!!.get("Level.upgrade.success")!!.replace("%level%",
                getLevel(Integer.parseInt(xp as String?).toDouble()).toString()
            ))!!.queue()
        }
    }
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
        event.replyEmbeds(EmbedBuilder().setTitle(language!!.get("Level.message.Info")).setDescription(message).setColor(
            Color.BLUE).build()).queue()
    }
}