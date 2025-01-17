package dev.zabricraft.replica.usecases.games

import dev.zabricraft.models.games.GameState
import dev.zabricraft.replica.Replica
import org.bukkit.GameMode
import org.bukkit.event.Cancellable
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockEvent
import org.bukkit.event.block.BlockPlaceEvent

class HandleBlockUpdateUseCase : IHandleBlockUpdateUseCase {

    override fun invoke(input: BlockEvent) {
        if (input !is Cancellable) return
        if (input.block.world.name != Replica.WORLD_NAME) return
        val player = (input as? BlockBreakEvent)?.player ?: (input as? BlockPlaceEvent)?.player ?: return

        val zp = Replica.instance?.getPlayer(player.uniqueId)
        if (zp != null && zp.buildmode) {
            input.isCancelled = false
            return
        }
        if (input.block.location.blockY != 64) {
            input.isCancelled = true
            return
        }
        if (input.block.location.blockZ < 0 || input.block.location.blockZ > 320) {
            input.isCancelled = true
            return
        }
        if (!input.block.type.toString().endsWith("_TERRACOTTA")) {
            input.isCancelled = true
            return
        }
        val col = input.block.chunk.z / 2 + 1
        val game = Replica.instance?.games?.find {
            it.state == GameState.IN_GAME &&
                    it.players.any { player.uniqueId == it }
        } ?: run {
            input.isCancelled = true
            return
        }
        if (zp?.plot != col) {
            input.isCancelled = true
            return
        }

        input.isCancelled = false
        if (game.isCompletingPlot(col)) {
            game.breakPlot(col)
            player.inventory.clear()
            player.updateInventory()
            player.gameMode = GameMode.SPECTATOR
            zp.finished = true
            game.mainHandler()
        }
    }

}
