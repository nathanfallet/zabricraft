package me.nathanfallet.zabricraft.database.players

import kotlinx.datetime.Clock
import me.nathanfallet.zabricraft.database.Database
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.*
import java.util.*

class DatabaseZabriPlayersRepository(
    private val database: Database
) : IZabriPlayersRepository {

    private val cachedPlayers = mutableMapOf<UUID, CachedPlayer>()

    init {
        database.dbQuery {
            SchemaUtils.create(ZabriPlayers)
        }
    }

    private fun getOrCreateCached(id: UUID): CachedPlayer {
        return cachedPlayers.getOrPut(id) {
            CachedPlayer(!Bukkit.getOnlineMode(), Clock.System.now())
        }
    }

    override fun create(payload: Player): ZabriPlayer? {
        return database.dbQuery {
            ZabriPlayers.insert {
                it[id] = payload.uniqueId
                it[name] = payload.name
            }
        }.resultedValues?.map {
            ZabriPlayers.toZabriPlayer(it, getOrCreateCached(it[ZabriPlayers.id].value))
        }?.singleOrNull()
    }

    override fun delete(id: UUID): Boolean {
        return database.dbQuery {
            ZabriPlayers.deleteWhere {
                Op.build { ZabriPlayers.id eq id }
            }
        } == 1
    }

    override fun get(id: UUID): ZabriPlayer? {
        return database.dbQuery {
            ZabriPlayers
                .select { ZabriPlayers.id eq id }
                .map {
                    ZabriPlayers.toZabriPlayer(it, getOrCreateCached(it[ZabriPlayers.id].value))
                }
                .singleOrNull()
        }
    }

    override fun update(id: UUID, payload: UpdateZabriPlayerPayload): Boolean {
        return database.dbQuery {
            ZabriPlayers.update({ ZabriPlayers.id eq id }) {
                payload.player?.let { value -> it[name] = value.name }
                payload.password?.let { value -> it[password] = value }
                payload.money?.let { value -> it[money] = value }
                payload.score?.let { value -> it[score] = value }
                payload.victories?.let { value -> it[victories] = value }
                payload.admin?.let { value -> it[admin] = value }
            }
        } == 1
    }

    override fun clearCache() {
        cachedPlayers.values.forEach { it.scoreboard.kill() }
        cachedPlayers.clear()
    }

    override fun clearCache(uuid: UUID) {
        cachedPlayers.remove(uuid)?.scoreboard?.kill()
    }

}
