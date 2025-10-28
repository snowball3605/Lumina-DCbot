
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate

object Main {
    var data: MutableMap<String?, Any?>? = null
    var language: String? = "en_UK"

    var my_obj: LocalDate = LocalDate.now()

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            Files.newInputStream(Paths.get("SETTING.yml")).use { `in` ->
                val yaml: Yaml = Yaml()
                data = yaml.load(`in`)
                language = data!!.get("lang").toString()
                val builder: JDA = JDABuilder.createDefault(data!!.get("TOKEN").toString())
                    .enableIntents(

                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_MEMBERS
                    )
                    .addEventListeners(Listener())
                    .build()

                val commands: CommandListUpdateAction = builder.updateCommands()

                commands.addCommands(
                    Commands.slash("info", "info about bot"),
                    Commands.slash("reload", "Reload the settig")
                )

                commands.queue()
                // LoggerManager.status(data!!.get("Logger.bot.running").toString(), LogLevel.INFO)
            }
        } catch (e: IOException) {
            // LoggerManager.status(e.message, LogLevel.ERROR)
        }
    }
}