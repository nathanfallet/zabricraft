package dev.zabricraft.replica.usecases.games

import dev.kaccelero.usecases.IUseCase
import org.bukkit.event.block.BlockEvent

interface IHandleBlockUpdateUseCase : IUseCase<BlockEvent, Unit>
