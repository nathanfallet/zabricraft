package dev.zabricraft.replica.events

import dev.zabricraft.models.games.GameState
import dev.zabricraft.replica.Replica
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerCommandPreprocess(
    private val getMessageUseCase: IGetMessageUseCase,
) : Listener {

    @EventHandler
    fun onPlayerCommandPreprocess(e: PlayerCommandPreprocessEvent) {
        val zp = Replica.instance?.getPlayer(e.player.uniqueId)?.takeIf {
            it.currentGame != 0
        } ?: return
        Replica.instance?.games?.find {
            it.id == zp.currentGame && it.state == GameState.IN_GAME
        } ?: return
        if (!e.message.equals("/replica leave", ignoreCase = true)) {
            e.isCancelled = true
            e.player.sendMessage("§c" + getMessageUseCase("cmd-error-only-leave"))
        }
    }

}
