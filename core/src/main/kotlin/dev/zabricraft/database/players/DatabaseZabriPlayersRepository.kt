package dev.zabricraft.database.players

import dev.kaccelero.models.IContext
import dev.kaccelero.repositories.Pagination
import dev.zabricraft.database.Database
import dev.zabricraft.models.players.CachedPlayer
import dev.zabricraft.models.players.UpdateZabriPlayerPayload
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository
import kotlinx.datetime.Clock
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.*
import java.util.*

class DatabaseZabriPlayersRepository(
    private val database: Database,
) : IZabriPlayersRepository {

    private val cachedPlayers = mutableMapOf<UUID, CachedPlayer>()

    init {
        database.transaction {
            SchemaUtils.create(ZabriPlayers)
        }
    }

    override fun create(payload: Player, context: IContext?): ZabriPlayer? {
        return database.transaction {
            ZabriPlayers.insert {
                it[id] = payload.uniqueId
                it[name] = payload.name
            }
        }.resultedValues?.map {
            ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
        }?.singleOrNull()
    }

    override fun delete(id: UUID, context: IContext?): Boolean {
        return database.transaction {
            ZabriPlayers.deleteWhere {
                Op.build { ZabriPlayers.id eq id }
            }
        } == 1
    }

    override fun get(id: UUID, context: IContext?): ZabriPlayer? {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .where { ZabriPlayers.id eq id }
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
                .singleOrNull()
        }
    }

    override fun list(context: IContext?): List<ZabriPlayer> {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
        }
    }

    override fun list(pagination: Pagination, context: IContext?): List<ZabriPlayer> {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .limit(pagination.limit.toInt(), pagination.offset)
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
        }
    }

    override fun getTopMoney(limit: Int): List<ZabriPlayer> {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .orderBy(ZabriPlayers.money, SortOrder.DESC)
                .limit(limit)
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
        }
    }

    override fun getTopScore(limit: Int): List<ZabriPlayer> {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .orderBy(ZabriPlayers.score, SortOrder.DESC)
                .limit(limit)
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
        }
    }

    override fun getTopVictories(limit: Int): List<ZabriPlayer> {
        return database.transaction {
            ZabriPlayers
                .selectAll()
                .orderBy(ZabriPlayers.victories, SortOrder.DESC)
                .limit(limit)
                .map {
                    ZabriPlayers.toZabriPlayer(it, getCached(it[ZabriPlayers.id].value))
                }
        }
    }

    override fun update(id: UUID, payload: UpdateZabriPlayerPayload, context: IContext?): Boolean {
        return database.transaction {
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

    override fun getCached(uuid: UUID): CachedPlayer {
        return cachedPlayers.getOrPut(uuid) {
            CachedPlayer(Bukkit.getOnlineMode(), Clock.System.now())
        }
    }

    override fun updateCached(uuid: UUID, cachedPlayer: CachedPlayer) {
        cachedPlayers[uuid] = cachedPlayer
    }

    override fun clearCache() {
        cachedPlayers.values.forEach { it.scoreboard.kill() }
        cachedPlayers.clear()
    }

    override fun clearCache(uuid: UUID) {
        cachedPlayers.remove(uuid)?.scoreboard?.kill()
    }

}
