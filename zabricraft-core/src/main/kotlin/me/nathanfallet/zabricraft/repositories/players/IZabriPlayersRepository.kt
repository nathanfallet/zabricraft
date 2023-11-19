package me.nathanfallet.zabricraft.repositories.players

import me.nathanfallet.usecases.models.repositories.IModelRepository
import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player
import java.util.*

interface IZabriPlayersRepository : IModelRepository<ZabriPlayer, UUID, Player, UpdateZabriPlayerPayload> {

    fun clearCache()
    fun clearCache(uuid: UUID)

}
