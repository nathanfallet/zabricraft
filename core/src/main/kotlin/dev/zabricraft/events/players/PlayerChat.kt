package dev.zabricraft.events.players

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class PlayerChat : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        event.format = "%s" + ChatColor.RESET + ": %s"
        if (player.isOp) {
            event.message = ChatColor.translateAlternateColorCodes('&', event.message)
        }
        Bukkit.getOnlinePlayers().forEach {
            if (event.message.lowercase().split(" ").contains(it.name.lowercase())) {
                it.playSound(it.location, Sound.BLOCK_NOTE_BLOCK_BELL, 10.0f, 1.0f)
                event.message = event.message.split(" ").joinToString(" ") { word ->
                    if (word.equals(it.name, true))
                        ChatColor.AQUA.toString() + "@" + it.name + ChatColor.RESET
                    else word
                }
            }
        }
        event.message = event.message.trim()
    }

}
