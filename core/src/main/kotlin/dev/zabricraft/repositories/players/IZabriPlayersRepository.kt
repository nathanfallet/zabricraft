package dev.zabricraft.repositories.players

import dev.kaccelero.repositories.IModelRepository
import dev.zabricraft.models.players.CachedPlayer
import dev.zabricraft.models.players.UpdateZabriPlayerPayload
import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player
import java.util.*

interface IZabriPlayersRepository : IModelRepository<ZabriPlayer, UUID, Player, UpdateZabriPlayerPayload> {

    fun getTopMoney(limit: Int): List<ZabriPlayer>
    fun getTopScore(limit: Int): List<ZabriPlayer>
    fun getTopVictories(limit: Int): List<ZabriPlayer>

    fun getCached(uuid: UUID): CachedPlayer
    fun updateCached(uuid: UUID, cachedPlayer: CachedPlayer)
    fun clearCache()
    fun clearCache(uuid: UUID)

}
