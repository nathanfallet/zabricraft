package me.nathanfallet.zabricraft.database.players

import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow

object ZabriPlayers : UUIDTable() {

    val name = varchar("name", 255)
    val password = varchar("password", 255).default("")
    val money = long("money").default(0)
    val score = long("score").default(0)
    val victories = long("victories").default(0)
    val admin = bool("admin").default(false)

    fun toZabriPlayer(
        row: ResultRow,
        cachedPlayer: CachedPlayer
    ) = ZabriPlayer(
        row[id].value,
        row[name],
        row[password],
        row[money],
        row[score],
        row[victories],
        row[admin],
        cachedPlayer.authenticated,
        cachedPlayer.loginAt,
        cachedPlayer.scoreboard
    )

}
