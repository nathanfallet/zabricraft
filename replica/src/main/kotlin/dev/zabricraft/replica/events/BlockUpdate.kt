package dev.zabricraft.replica.events

import dev.zabricraft.replica.usecases.games.IHandleBlockUpdateUseCase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class BlockUpdate(
    private val handleBlockUpdateUseCase: IHandleBlockUpdateUseCase,
) : Listener {

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockBreakEvent) = handleBlockUpdateUseCase(e)

    @EventHandler(ignoreCancelled = true)
    fun onBlockBreak(e: BlockPlaceEvent) = handleBlockUpdateUseCase(e)

}
