package dev.zabricraft.replica.events

import dev.zabricraft.models.games.GameState
import dev.zabricraft.replica.Replica
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BlockPlace : Listener {

    // TODO: Merge with block break event
    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockPlaceEvent) {
        if (e.block.world.name != "Replica") return

        val zp = Replica.instance?.getPlayer(e.player.uniqueId)
        if (zp != null && zp.buildmode) {
            e.isCancelled = false
            return
        }
        if (e.block.location.blockY != 64) {
            e.isCancelled = true
            return
        }
        if (e.block.location.blockZ < 0 || e.block.location.blockZ > 320) {
            e.isCancelled = true
            return
        }
        if (!e.block.type.toString().endsWith("_TERRACOTTA")) {
            e.isCancelled = true
            return
        }
        val col = e.block.chunk.z / 2 + 1
        val game = Replica.instance?.games?.find {
            it.state == GameState.IN_GAME &&
                    it.players.any { e.player.uniqueId == it }
        } ?: run {
            e.isCancelled = true
            return
        }
        if (zp?.plot != col) {
            e.isCancelled = true
            return
        }

        e.isCancelled = false
        if (game.isCompletingPlot(col)) {
            game.breakPlot(col)
            e.player.inventory.clear()
            e.player.updateInventory()
            e.player.gameMode = GameMode.SPECTATOR
            zp.finished = true
            game.mainHandler()
        }
    }

}
