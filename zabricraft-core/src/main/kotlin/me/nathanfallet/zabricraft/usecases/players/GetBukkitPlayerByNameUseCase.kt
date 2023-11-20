package me.nathanfallet.zabricraft.usecases.players

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GetBukkitPlayerByNameUseCase : IGetBukkitPlayerByNameUseCase {

    override fun invoke(input: String): Player? {
        return Bukkit.getPlayer(input)
    }


}
