
import Util.LanguageManager
import com.onmi_tech.LogLevel
import com.onmi_tech.SNLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

val sn: SNLogger = SNLogger(LogLevel.DEBUG)
var data: MutableMap<String?, Any?>? = null
var lang_data: MutableMap<String?, Any?>? = null
var jda: JDA? = null
fun main() {
    Files.newInputStream(Paths.get("SETTING.yml")).use { `in` ->
        val yaml = Yaml()
        data = yaml.load(`in`)
    }

    jda = JDABuilder.createDefault(data!!.get("TOKEN").toString()).enableIntents(GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.MESSAGE_CONTENT,
        GatewayIntent.GUILD_MEMBERS,
        GatewayIntent.GUILD_PRESENCES).build()

    PluginLoader.loadPlugins(jda as JDA, data)
}

