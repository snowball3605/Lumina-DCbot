package com.onmi_tech

import com.onmi_tech.LogLevel
import com.onmi_tech.Main
import com.onmi_tech.SNLogger


object LoggerManager {
    var sn_logger: SNLogger? = null

    fun status(message: String?, level: LogLevel) {
        sn_logger = SNLogger(level as com.onmi_tech.LogLevel?)
        if (Data!!.get("Logger") as Boolean) {
            sn_logger?.log(level, message)
        }
    }
}
