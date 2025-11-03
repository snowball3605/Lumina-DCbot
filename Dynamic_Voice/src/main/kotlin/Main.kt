package com.onmi_tech

import Plugin
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.String
import kotlin.collections.MutableList

class Main: Plugin, ListenerAdapter() {
    var Data: MutableMap<String?, Any?>? = null
    override val name = "Dynamic_Voice"

    override fun onEnable(
        jda: JDA,
        data: MutableMap<String?, Any?>?
    ) {
        jda.addEventListener(this)
        val folder = File("plugins/Dynamic_Voice")
        val setting = File(folder, "SETTING.yml")
        folder.mkdirs()
        if (!setting.exists()) {
            val inputStream = object {}.javaClass.getResourceAsStream("/SETTING.yml")
                ?: throw FileNotFoundException("SETTING.yml does not exists.")

            inputStream.use { input ->
                setting.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        Files.newInputStream(Paths.get("plugins/Dynamic_Voice/SETTING.yml")).use {`in` ->
            val yaml: Yaml = Yaml()
            Data = yaml.load(`in`)
        }
    }

    override fun onDisable() {
    }

    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        if (Data!!.isEmpty()) return
        val string = Data!!.get("TargetChannel_TargetCategory") as MutableList<String>
        for (string in string) {
            val split: Array<String?> = string.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (event.channelJoined != null) {
                if (event.channelJoined!!.idLong == split[0]!!.toLong()) {
                    event.jda.getCategoryById(split[1]!!.toLong())!!.createVoiceChannel(
                        Data!!.get("Voice_Channel_Name").toString().replace("%user_name%", event.member.user.name)
                    ).queue({ k ->
                        val guild: Guild = event.guild
                        guild.moveVoiceMember(event.member, k).queue()
                    })
                }
            }
            if (event.channelLeft != null) {
            if (event.getChannelLeft()?.getParentCategoryIdLong() == split[1]!!.toLong() && event.getChannelLeft()
                    ?.getMembers()?.isEmpty() == true && (event.getChannelLeft()?.getIdLong() != split[0]!!.toLong())
            ) {
                event.getChannelLeft()?.delete()?.queue()
            }
            }
        }
    }
}