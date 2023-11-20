package me.nathanfallet.zabricraft.usecases.rules

import me.nathanfallet.usecases.base.IGenericUseCase
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event

interface IWorldProtectionRuleUseCase : IGenericUseCase {

    fun isProtected(location: Location): Boolean
    fun isAllowedInProtectedLocation(
        player: Player,
        location: Location,
        event: Event
    ): Boolean

}
