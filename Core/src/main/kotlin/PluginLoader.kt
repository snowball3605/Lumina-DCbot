import net.dv8tion.jda.api.JDA
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.net.URLClassLoader
import java.util.jar.JarEntry
import java.util.jar.JarFile

object PluginLoader {
    private val plugins = mutableListOf<Plugin>()
    private val pluginFolder = File("plugins")
    private val yaml = Yaml()

    fun loadPlugins(jda: net.dv8tion.jda.api.JDA, Data: MutableMap<String?, Any?>?) {
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs()
            sn.warn("Created plugins folder.")
            return
        }

        val jarFiles = pluginFolder.listFiles { _, name -> name.endsWith(".jar") } ?: return

        if (jarFiles.isEmpty()) {
            sn.error("No plugins found in ${pluginFolder.absolutePath}")
            return
        }

        jarFiles.forEach { jarFile ->
            try {
                loadPluginFromJar(jarFile, jda, Data)
            } catch (e: Exception) {
                sn.error("Failed to load plugin ${jarFile.name}: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun loadPluginFromJar(jarFile: File, jda: JDA, Data: MutableMap<String?, Any?>?) {
        val jar = JarFile(jarFile)

        val pluginYmlEntry: JarEntry = jar.getJarEntry("plugin.yml")
            ?: throw IllegalArgumentException("plugin.yml not found in ${jarFile.name}")

        val pluginConfig = jar.getInputStream(pluginYmlEntry).use { input ->
            yaml.loadAs(input, Map::class.java) as Map<String, Any>
        }

        val name = pluginConfig["name"] as? String
            ?: throw IllegalArgumentException("Missing 'name' in plugin.yml")
        val version = pluginConfig["version"] as? String ?: "unknown"
        val mainClassName = pluginConfig["main"] as? String
            ?: throw IllegalArgumentException("Missing 'main' class in plugin.yml")
        val author = pluginConfig["author"] as? String ?: "unknown"

        val classLoader = URLClassLoader(arrayOf(jarFile.toURI().toURL()), this.javaClass.classLoader)

        val pluginClass = Class.forName(mainClassName, true, classLoader)
        val pluginInstance = pluginClass.getDeclaredConstructor().newInstance() as? Plugin
            ?: throw IllegalArgumentException("$mainClassName must implement Plugin interface")

        plugins.add(pluginInstance)
        try {
            pluginInstance.onEnable(jda, Data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        sn.info("Loaded plugin: $name $version by $author")
        jar.close()
    }

    fun disableAll() {
        plugins.forEach {
            try {
                it.onDisable()
                sn.info("Disabled plugin: ${it.name}")
            } catch (e: Exception) {
                sn.error("Error disabling ${it.name}: ${e.message}")
            }
        }
        plugins.clear()
    }
}