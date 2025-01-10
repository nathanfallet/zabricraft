package dev.zabricraft.usecases.rules

import dev.kaccelero.usecases.IGenericUseCase
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event

interface IWorldProtectionRuleUseCase : IGenericUseCase {

    fun isProtected(location: Location): Boolean
    fun isAllowedInProtectedLocation(
        player: Player,
        location: Location,
        event: Event,
    ): Boolean

}
