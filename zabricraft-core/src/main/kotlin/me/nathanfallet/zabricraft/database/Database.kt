package me.nathanfallet.zabricraft.database

import org.jetbrains.exposed.sql.transactions.transaction

class Database(
    protocol: String,
    host: String = "",
    name: String = "",
    user: String = "",
    password: String = "",
    port: Int = 0
) {

    private val database: org.jetbrains.exposed.sql.Database

    init {
        // Connect to database
        database = when (protocol) {
            "mysql" -> org.jetbrains.exposed.sql.Database.connect(
                "jdbc:mysql://$host:$port/$name", "com.mysql.cj.jdbc.Driver",
                user, password
            )

            "h2" -> org.jetbrains.exposed.sql.Database.connect(
                "jdbc:h2:mem:$name;DB_CLOSE_DELAY=-1;", "org.h2.Driver"
            )

            else -> throw Exception("Unsupported database protocol: $protocol")
        }
    }

    fun disconnect() = database.connector().close()

    fun <T> dbQuery(block: () -> T): T = transaction(database) { block() }

}
