package Event

import Main
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color


class Join_Leave : ListenerAdapter() {
    public override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        if (Main.data!!.get("JOIN_CHANNEL") == null) return
        val text_channel: TextChannel? =
            event.getGuild().getTextChannelById((Main.data!!.get("JOIN_CHANNEL") as String?)!!.toLong())
        text_channel?.sendMessageEmbeds(
            EmbedBuilder().setImage(event.member.effectiveAvatarUrl).setColor(Color.BLUE)
                .setTitle("Join Channel")
                .setDescription("Welcome " + event.member.user.name + "\nJoined Time: " + Main.my_obj)
                .build()
        )?.queue()
    }
}