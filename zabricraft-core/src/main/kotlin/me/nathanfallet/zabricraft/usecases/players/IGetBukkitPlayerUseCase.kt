package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.usecases.base.IUseCase
import org.bukkit.entity.Player
import java.util.*

interface IGetBukkitPlayerUseCase : IUseCase<UUID, Player?>
