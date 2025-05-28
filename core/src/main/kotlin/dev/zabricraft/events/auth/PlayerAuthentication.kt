package dev.zabricraft.events.auth

import dev.zabricraft.usecases.players.IGetCachedZabriPlayerUseCase
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.entity.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*

class PlayerAuthentication(
    private val getCachedZabriPlayerUseCase: IGetCachedZabriPlayerUseCase,
) : Listener {

    private fun checkAuthenticated(player: Player, event: Cancellable) {
        if (!getCachedZabriPlayerUseCase(player.uniqueId).authenticated) event.isCancelled = true
    }

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val cmd = event.message.split(" ").first().lowercase()
        if (cmd == "/login" || cmd == "/register") return
        checkAuthenticated(event.player, event)
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerInteractAtEntity(event: PlayerInteractAtEntityEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onPlayerShear(event: PlayerShearEntityEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerEditBook(event: PlayerEditBookEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onSignChange(event: SignChangeEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerPickupItem(event: PlayerPickupItemEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) = checkAuthenticated(event.player, event)

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onEntityAirChange(event: EntityAirChangeEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val player = event.damager as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onEntityTarget(event: EntityTargetEvent) {
        val player = event.target as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onFoodLevelChange(event: FoodLevelChangeEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onEntityRegainHealth(event: EntityRegainHealthEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

    @EventHandler
    fun onEntityShootBow(event: EntityShootBowEvent) {
        val player = event.entity as? Player ?: return
        checkAuthenticated(player, event)
    }

}
