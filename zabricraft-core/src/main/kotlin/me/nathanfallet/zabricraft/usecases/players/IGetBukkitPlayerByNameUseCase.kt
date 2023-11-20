package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.usecases.base.IUseCase
import org.bukkit.entity.Player

interface IGetBukkitPlayerByNameUseCase : IUseCase<String, Player?>
