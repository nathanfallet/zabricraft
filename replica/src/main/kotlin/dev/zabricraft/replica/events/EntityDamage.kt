package dev.zabricraft.replica.events

import dev.zabricraft.replica.Replica
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class EntityDamage : Listener {

    @EventHandler
    fun onEntityDamage(e: EntityDamageEvent) {
        if (e.entity is Player && e.entity.world.name == Replica.WORLD_NAME && e.entity.location.y > 0) {
            e.isCancelled = true
        }
    }

}
