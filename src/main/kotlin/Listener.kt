
import Commands.Info
import Commands.Rank
import Commands.Reload
import Event.Dynamic_Voice
import Event.Experience_SendMessage
import Event.Join_Leave
import Util.LoggerManager
import com.onmi_tech.LogLevel
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.jetbrains.exposed.sql.SqlExpressionBuilder.rank

class Listener : ListenerAdapter() {
    var info: Info = Info()
    var join_leave: Join_Leave = Join_Leave()
    var dynamic_voice: Dynamic_Voice = Dynamic_Voice()
    var reload: Reload = Reload()
    var Experience_SendMessage: Experience_SendMessage = Experience_SendMessage()
    var Rank: Rank = Rank()
    public override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        var message = Main.data!!.get("Logger.bot.commands").toString()
        message = message.replace("%commands%", event.getName())
         LoggerManager.status(event.getName() + " " + message, LogLevel.INFO)
        reload.onSlashCommandInteraction(event)
        info.onSlashCommandInteraction(event)
        Rank.onSlashCommandInteraction(event)
    }

    public override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
         LoggerManager.status(Main.data!!.get("Logger.guild.member.join").toString(), LogLevel.INFO)
        join_leave.onGuildMemberJoin(event)
    }

    public override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
         LoggerManager.status(Main.data!!.get("Logger.guild.voice.update").toString(), LogLevel.INFO)
        dynamic_voice.onGuildVoiceUpdate(event)
    }

    override fun onMessageReceived(event: MessageReceivedEvent) {
        Experience_SendMessage.onMessageReceived(event)
    }
}