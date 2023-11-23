package me.nathanfallet.zabricraft.repositories.players

import me.nathanfallet.usecases.models.repositories.IModelRepository
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
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
