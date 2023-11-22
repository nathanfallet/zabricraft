package me.nathanfallet.zabricraft.events.rules

import me.nathanfallet.zabricraft.usecases.rules.IListWorldProtectionRuleUseCase
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockIgniteEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.entity.*
import org.bukkit.event.player.*

class WorldProtection(
    private val listWorldProtectionRuleUseCase: IListWorldProtectionRuleUseCase
) : Listener {

    private fun checkAllowed(player: Player, location: Location, event: Event, cancellable: Cancellable) {
        listWorldProtectionRuleUseCase().firstOrNull {
            it.isProtected(location) && !it.isAllowedInProtectedLocation(
                player,
                location,
                event
            )
        }?.let {
            cancellable.isCancelled = true
            player.sendMessage(ChatColor.RED.toString() + "Vous n'avez pas le droit de faire ça ici !")
        }
    }

    private fun checkAllowed(location: Location, cancellable: Cancellable) {
        listWorldProtectionRuleUseCase().firstOrNull {
            it.isProtected(location)
        }?.let {
            cancellable.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        checkAllowed(event.player, event.clickedBlock?.location ?: event.player.location, event, event)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        checkAllowed(event.player, event.block.location, event, event)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        checkAllowed(event.player, event.block.location, event, event)
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        checkAllowed(event.player, event.rightClicked.location, event, event)
    }

    @EventHandler
    fun onPlayerInteractAtEntity(event: PlayerInteractAtEntityEvent) {
        checkAllowed(event.player, event.rightClicked.location, event, event)
    }

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return
        checkAllowed(player, player.location, event, event)
    }

    @EventHandler
    fun onPlayerShear(event: PlayerShearEntityEvent) {
        checkAllowed(event.player, event.entity.location, event, event)
    }

    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) {
        checkAllowed(event.player, event.caught?.location ?: event.player.location, event, event)
    }

    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        checkAllowed(event.player, event.bed.location, event, event)
    }

    @EventHandler
    fun onSignChange(event: SignChangeEvent) {
        checkAllowed(event.player, event.block.location, event, event)
    }

    @EventHandler
    fun onPlayerPickupItem(event: PlayerPickupItemEvent) {
        checkAllowed(event.player, event.item.location, event, event)
    }

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        checkAllowed(event.player, event.itemDrop.location, event, event)
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val player = event.entity as? Player ?: return
        checkAllowed(player, player.location, event, event)
    }

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val player = event.damager as? Player ?: return
        checkAllowed(player, event.entity.location, event, event)
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        val player = event.entity as? Player ?: return
        checkAllowed(player, player.location, event, event)
    }

    @EventHandler
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        val player = event.entity.shooter as? Player ?: return
        checkAllowed(player, player.location, event, event)
    }

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        val player = event.entity as? Player ?: return
        checkAllowed(player, player.location, event, event)
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        checkAllowed(event.entity.location, event)
    }

    @EventHandler
    fun onBlockIgnite(event: BlockIgniteEvent) {
        checkAllowed(event.block.location, event)
    }

}
