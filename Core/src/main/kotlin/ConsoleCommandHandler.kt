import net.dv8tion.jda.api.JDA
import java.util.Scanner

class ConsoleCommandHandler(val jda: JDA, val pluginLoader: PluginLoader): Thread("Console-Thread") {

    private val scanner = Scanner(System.`in`)
    private var running = true

    override fun run() {
        println("Console command listener started. Type '/reload' to reload plugins.")

        while (running && scanner.hasNextLine()) {
            val input = scanner.nextLine().trim()

            when {
                input.equals("/reload", ignoreCase = true) -> {
                    println("[Console] Reloading all plugins...")
                    try {
                        pluginLoader.reloadPlugins(jda)
                        println("[Console] All plugins reloaded successfully!")
                    } catch (e: Exception) {
                        println("[Console] Reload failed: ${e.message}")
                        e.printStackTrace()
                    }
                }
                input.isNotEmpty() -> {
                    println("[Console] Unknown command: $input (type /help)")
                }
            }
        }
    }
    fun stopListening() {
        running = false
        scanner.close()
    }
}