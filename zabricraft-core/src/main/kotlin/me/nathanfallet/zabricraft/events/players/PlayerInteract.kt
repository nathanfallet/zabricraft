package me.nathanfallet.zabricraft.events.players

import dev.kaccelero.commons.repositories.IGetModelUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.usecases.games.IJoinGameUseCase
import me.nathanfallet.zabricraft.usecases.games.IListGameUseCase
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class PlayerInteract(
    private val listGameUseCase: IListGameUseCase,
    private val joinGameUseCase: IJoinGameUseCase,
    private val getZabriPlayerUseCase: IGetModelUseCase<ZabriPlayer, UUID>,
) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK || event.clickedBlock?.type != Material.OAK_WALL_SIGN) return
        val game = listGameUseCase().firstOrNull { game ->
            game.signs.any { it == event.clickedBlock?.location }
        } ?: return
        val zabriPlayer = getZabriPlayerUseCase(event.player.uniqueId) ?: return
        joinGameUseCase(game, event.player, zabriPlayer)
    }

}
