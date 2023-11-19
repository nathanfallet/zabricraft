package me.nathanfallet.zabricraft.models.rules

import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Event

interface IWorldProtectionRule {

    fun isProtected(location: Location): Boolean
    fun isAllowedInProtectedLocation(
        player: Player,
        zabriPlayer: ZabriPlayer,
        location: Location,
        event: Event
    ): Boolean

}
