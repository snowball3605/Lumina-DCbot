
import Util.LanguageManager
import Util.LoggerManager
import Util.getAll
import Util.init
import com.onmi_tech.LogLevel
import com.onmi_tech.SNLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.jetbrains.exposed.sql.Database
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate

object Main {
    var data: MutableMap<String?, Any?>? = null
    var language: String? = "en_UK"
    var database_link = "jdbc:sqlite:data/test.db"
    var my_obj: LocalDate = LocalDate.now()
    var Connection: Connection? = null

    val sn: SNLogger = SNLogger(LogLevel.DEBUG)

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
                    Commands.slash("info", LanguageManager.get("Info.message").toString()),
                    Commands.slash("reload", LanguageManager.get("Reload.Info").toString()),
                    Commands.slash("rank", LanguageManager.get("Level.message.Info").toString()),
                )

                commands.queue()
                Class.forName("org.sqlite.JDBC")
                Connection = DriverManager.getConnection(database_link)
                init()
                LoggerManager.status(data!!.get("Logger.bot.running").toString(), LogLevel.INFO)
            }
        } catch (e: IOException) {
             LoggerManager.status(e.message, LogLevel.ERROR)
        }
    }
}