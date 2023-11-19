package me.nathanfallet.zabricraft.usecases.players

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GetBukkitPlayerUseCase : IGetBukkitPlayerUseCase {

    override fun invoke(input: UUID): Player? {
        return Bukkit.getPlayer(input)
    }

}
