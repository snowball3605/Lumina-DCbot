package Util

import Main
import org.yaml.snakeyaml.Yaml
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


object LanguageManager {
    fun get(key: String?): String? {
        try {
            Files.newInputStream(Paths.get("lang/" + Main.language + ".yml")).use { `in` ->
                val yaml: Yaml = Yaml()
                val data: MutableMap<String?, Any?>? = yaml.load(`in`)
                if (data == null) {
                } else {
                    return data.get(key) as String?
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return null
    }
}