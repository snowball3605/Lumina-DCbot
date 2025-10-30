package Util

import java.sql.DriverManager

fun init() {
    DriverManager.getConnection(Main.database_link).use {
        val statement = Main.Connection?.createStatement()
        statement?.execute("""
                CREATE TABLE IF NOT EXISTS level (
                id VARCHAR(20) NOT NULL PRIMARY KEY,
                exp VARCHAR(20) NOT NULL
                )
            """)
        statement?.close()
    }
}

fun write(MySQl: String): Boolean {
    val preparedStatement = Main.Connection?.prepareStatement(MySQl)

    return preparedStatement?.executeUpdate()!! > 0

}

fun delete(Table: String,Key_Column: String , Key: String): Boolean {
    DriverManager.getConnection(Main.database_link).use {
        connection ->
        val sql = "DELETE FROM `$Table` WHERE `$Key_Column` = $Key"
        val statement = connection.prepareStatement(sql)

        return statement.executeUpdate() > 0
    }
}

fun get(Table: String, Column: String, Key_Column: String, Key: String): Any? {
    DriverManager.getConnection(Main.database_link).use {
        val statement = Main.Connection?.createStatement()
        val resultSet = statement?.executeQuery("""
            SELECT `$Column` FROM `$Table` WHERE `$Key_Column` = '$Key';
        """)
        return if (resultSet?.next() == true) {
            resultSet.getObject(Column)
        } else {
            null
        }
    }
}

fun getAll(MySQl: String, Column: String): Any {
    var s = mutableListOf<Any>()
    DriverManager.getConnection(Main.database_link).use {
        val statement = Main.Connection?.createStatement()
        val resultSet = statement?.executeQuery(MySQl)
         while (resultSet?.next() == true) {
             s.add(resultSet.getLong(Column))
         }
        return s
    }
}