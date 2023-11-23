package me.nathanfallet.zabricraft.events.players

import io.mockk.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerChatTest {

    @Test
    fun testChat() {
        val player = mockk<Player>()
        val event = AsyncPlayerChatEvent(true, player, "Hello world!", setOf())
        mockkStatic(Bukkit::class)
        every { player.isOp } returns false
        every { player.name } returns "Player"
        every { Bukkit.getOnlinePlayers() } returns setOf(player)
        PlayerChat().onPlayerChat(event)
        assertEquals("%s" + ChatColor.RESET + ": %s", event.format)
        assertEquals("Hello world!", event.message)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testChatColorNotOp() {
        val player = mockk<Player>()
        val event = AsyncPlayerChatEvent(true, player, "&aHello world!", setOf())
        mockkStatic(Bukkit::class)
        every { player.isOp } returns false
        every { player.name } returns "Player"
        every { Bukkit.getOnlinePlayers() } returns setOf(player)
        PlayerChat().onPlayerChat(event)
        assertEquals("%s" + ChatColor.RESET + ": %s", event.format)
        assertEquals("&aHello world!", event.message)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testChatColorOp() {
        val player = mockk<Player>()
        val event = AsyncPlayerChatEvent(true, player, "&aHello world!", setOf())
        mockkStatic(Bukkit::class)
        every { player.isOp } returns true
        every { player.name } returns "Player"
        every { Bukkit.getOnlinePlayers() } returns setOf(player)
        PlayerChat().onPlayerChat(event)
        assertEquals("%s" + ChatColor.RESET + ": %s", event.format)
        assertEquals(ChatColor.GREEN.toString() + "Hello world!", event.message)
        unmockkStatic(Bukkit::class)
    }

    @Test
    fun testMention() {
        val player = mockk<Player>()
        val event = AsyncPlayerChatEvent(true, player, "Hello player", setOf())
        val location = mockk<Location>()
        mockkStatic(Bukkit::class)
        every { player.isOp } returns false
        every { player.name } returns "Player"
        every { player.location } returns location
        every { player.playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 10.0f, 1.0f) } returns Unit
        every { Bukkit.getOnlinePlayers() } returns setOf(player)
        PlayerChat().onPlayerChat(event)
        assertEquals("%s" + ChatColor.RESET + ": %s", event.format)
        assertEquals("Hello " + ChatColor.AQUA + "@Player" + ChatColor.RESET, event.message)
        verify { player.playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 10.0f, 1.0f) }
        unmockkStatic(Bukkit::class)
    }

}
