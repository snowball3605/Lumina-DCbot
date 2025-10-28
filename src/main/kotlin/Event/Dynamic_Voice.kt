package Event

import Main
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter


class Dynamic_Voice : ListenerAdapter() {
    public override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        val id = Main.data!!.get("Dynamic_Voice_ID") as MutableList<String>

        for (s in id) {
            val split: Array<String?> = s.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val channel_id = split[0]!!.toLong()
            val classification_id = split[1]!!.toLong()

            if (event.getChannelLeft() != null) {
                if (event.getChannelLeft()?.getParentCategoryIdLong() == classification_id && event.getChannelLeft()
                        ?.getMembers()?.isEmpty() == true && (event.getChannelLeft()?.getIdLong() != channel_id)
                ) {
                    event.getChannelLeft()?.delete()?.queue()
                }
            }
            if (event.getChannelJoined() != null) {
                if (event.getChannelJoined()?.getIdLong() == channel_id) {
                    var channel_name = Main.data!!.get("Dynamic_Voice_Name").toString()
                    channel_name = channel_name.replace("%Voice_Name%", event.member.user.effectiveName)
                    event.getJDA().getCategoryById(classification_id)?.createVoiceChannel(channel_name)?.queue({ k ->
                        val guild: Guild = event.guild
                        guild.moveVoiceMember(event.member, k).queue()
                    })
                }
            }
        }
    }
}