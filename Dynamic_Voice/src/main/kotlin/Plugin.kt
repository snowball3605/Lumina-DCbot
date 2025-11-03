import net.dv8tion.jda.api.JDA

interface Plugin {
    val name: String

    fun onEnable(jda: JDA, data: MutableMap<String?, Any?>?)
    fun onDisable()
}