package com.onmi_tech

import Plugin
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths

var Data: MutableMap<String?, Any?>? = null
var language: MutableMap<String?, Any?>? = null
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Main(): Plugin, ListenerAdapter() {
    override val name = "Join_Leave"
    override fun onEnable(jda: JDA, data: MutableMap<String?, Any?>?) {
        jda.addEventListener(this)
        val folder = File("plugins/Join_Leave")
        val setting = File(folder, "SETTING.yml")
        folder.mkdirs()
        if (!setting.exists()) {
            val  inputStream = object {}.javaClass.classLoader.getResourceAsStream("SETTING.yml")
                ?: throw FileNotFoundException("Setting.yml not found")

            inputStream.use { input ->
                setting.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        val folder_ = File("plugins/Join_Leave/lang")
        val language_ = File(folder_, "en_UK.yml")
        folder_.mkdirs()
        if (!language_.exists()) {
            val  inputStream = object {}.javaClass.classLoader.getResourceAsStream("lang/en_UK.yml")
            ?: throw FileNotFoundException("lang/en_UK.yml not found")

            inputStream.use { input ->
                language_.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }

        Files.newInputStream(Paths.get("plugins/Join_Leave/SETTING.yml")).use { input ->
            val yaml = Yaml()
            Data = yaml.load(input)
        }
        Files.newInputStream(Paths.get("plugins/Join_Leave/lang/" + Data!!.get("lang") + ".yml")).use { input ->
            val yaml = Yaml()
            language = yaml.load(input)
        }
    }

    override fun onDisable() {
    }

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        var descriptor = language!!.get("Welcome.description").toString().replace("%mention%", event.member.asMention).replace("%user_name%", event.user.name).replace("%guild_name%", event.guild.name)
        event.jda.getTextChannelById(Data!!.get("Join_Channel").toString())!!.sendMessageEmbeds(EmbedBuilder().setTitle(language!!.get("Welcome.Title").toString())
                .setDescription(descriptor).build()).queue()
    }
}

