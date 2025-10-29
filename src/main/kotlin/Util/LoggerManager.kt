package Util

import Main
import com.onmi_tech.LogLevel
import com.onmi_tech.SNLogger



object LoggerManager {
    var sn_logger: SNLogger? = null

    fun status(message: String?, level: LogLevel) {
        sn_logger = SNLogger(level as com.onmi_tech.LogLevel?)
        if (Main.data!!.get("Logger_Output") == null) {
            sn_logger?.log(level, message)
        }
    }
}
