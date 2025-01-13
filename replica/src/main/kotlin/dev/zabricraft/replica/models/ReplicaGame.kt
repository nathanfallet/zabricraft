package dev.zabricraft.replica.models

import dev.kaccelero.commons.repositories.IGetModelUseCase
import dev.zabricraft.models.games.GameState
import dev.zabricraft.models.games.IGame
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.replica.Replica
import dev.zabricraft.usecases.messages.IGetMessageUseCase
import dev.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import java.util.*

class ReplicaGame(
    override val id: Int,
    override val signs: MutableList<Location> = mutableListOf(),
    override var state: GameState = GameState.WAITING,
    override var currentCountValue: Int = 0,
) : IGame {

    override val minPlayers: Int
        get() = 2

    override val maxPlayers: Int
        get() = 10

    override val name: String
        get() = "Replica"

    override val players: List<UUID>
        get() = Bukkit.getOnlinePlayers().map { it.uniqueId }.filter {
            val zp = Replica.instance?.getPlayer(it)
            zp?.currentGame == id && (state != GameState.IN_GAME && state != GameState.FINISHED || zp.playing) && !zp.buildmode
        }

    override val allPlayers: List<UUID>
        get() = Bukkit.getOnlinePlayers().map { it.uniqueId }.filter {
            val zp = Replica.instance?.getPlayer(it)
            zp?.currentGame == id && !zp.buildmode
        }

    override fun mainHandler() {
        val players = players
        var number = players.size
        var current = 0
        var no: UUID? = null
        players.mapNotNull {
            Replica.instance?.getPlayer(it)
        }.forEach { zp ->
            if (zp.finished) current++
            else no = zp.uuid
        }
        if (number == 0 || number == 1) stop()
        else if (current >= number - 1) {
            no?.let {
                val nop = Bukkit.getPlayer(no)
                val zp = Replica.instance?.getPlayer(no)
                zp?.playing = false
                zp?.finished = false
                zp?.plot = 0
                allPlayers.mapNotNull {
                    Bukkit.getPlayer(it)
                }.forEach { player ->
                    player.sendMessage(
                        "§7" + get().get<IGetMessageUseCase>()("chat-lose-public").replace("%s", nop?.name ?: "")
                    )
                }
                nop?.inventory?.clear()
                nop?.updateInventory()
                nop?.gameMode = GameMode.SPECTATOR
                nop?.sendMessage("§7" + get().get<IGetMessageUseCase>()("chat-lose-private"))
            }
            if (number == 2) stop()
            else loadDraw()
        }
    }

    override fun start() {
        allPlayers.forEach { uuid ->
            val zp = Replica.instance?.getPlayer(uuid)
            zp?.playing = true
        }
        state = GameState.IN_GAME
        loadDraw()
    }

    override fun stop() {
        if (state != GameState.IN_GAME) return
        state = GameState.FINISHED
        currentCountValue = 0
        Bukkit.getPlayer(players[0])?.let { player ->
            Bukkit.broadcastMessage(
                "§7" + get().get<IGetMessageUseCase>()("chat-win-public").replace("%s", player.name)
            )
            player.inventory.clear()
            player.updateInventory()
            player.gameMode = GameMode.SPECTATOR
            player.sendMessage("§a" + get().get<IGetMessageUseCase>()("chat-win-private"))

            get().get<IGetModelUseCase<ZabriPlayer, UUID>>(named<ZabriPlayer>())(player.uniqueId)?.apply {
                // TODO: Update score, victories, money
            }
            // TODO: Same for ReplicaPlayer
        }
        loadPlots()
        Bukkit.getScheduler().scheduleSyncDelayedTask(Replica.instance!!, Runnable {
            allPlayers.mapNotNull {
                val player = Bukkit.getPlayer(it)
                val zp = Replica.instance?.getPlayer(it)
                if (player != null && zp != null) Pair(player, zp)
                else null
            }.forEach { pair ->
                // TODO: Extract and merge (similar to ReplicaCommand leave)
                pair.second.playing = false
                pair.second.finished = false
                pair.second.plot = 0
                pair.second.currentGame = 0
                pair.first.teleport(get().get<IGetSetSpawnUseCase>()())
                pair.first.gameMode = GameMode.SURVIVAL
                pair.first.inventory.clear()
                pair.first.updateInventory()
            }
            state = GameState.WAITING
        }, 100)
    }

    override fun reset() {
        allPlayers.mapNotNull {
            Bukkit.getPlayer(it)
        }.forEach { player ->
            player.sendMessage("§c" + get().get<IGetMessageUseCase>()("reload-msg"))
            player.teleport(get().get<IGetSetSpawnUseCase>()())
            player.gameMode = GameMode.SURVIVAL
            player.inventory.clear()
            player.updateInventory()
        }
        loadPlots()
    }

    override fun join(player: Player, zabriPlayer: ZabriPlayer) {
        Replica.instance?.getPlayer(player.uniqueId)?.currentGame = id
    }

    fun makeClay(color: Int): Material {
        return when (color) {
            0 -> Material.WHITE_TERRACOTTA
            1 -> Material.ORANGE_TERRACOTTA
            2 -> Material.MAGENTA_TERRACOTTA
            3 -> Material.LIGHT_BLUE_TERRACOTTA
            4 -> Material.YELLOW_TERRACOTTA
            5 -> Material.LIME_TERRACOTTA
            6 -> Material.PINK_TERRACOTTA
            7 -> Material.GRAY_TERRACOTTA
            8 -> Material.LIGHT_GRAY_TERRACOTTA
            9 -> Material.CYAN_TERRACOTTA
            10 -> Material.PURPLE_TERRACOTTA
            11 -> Material.BLUE_TERRACOTTA
            12 -> Material.BROWN_TERRACOTTA
            13 -> Material.GREEN_TERRACOTTA
            14 -> Material.RED_TERRACOTTA
            else -> Material.BLACK_TERRACOTTA
        }
    }

    fun loadPlots() {
        for (i in 0..19) {
            for (x in 5..12) {
                for (z in 5..12) {
                    Location(
                        Bukkit.getWorld("Replica"),
                        (x + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                        64.0,
                        (z + i * 32).toDouble()
                    ).block.type = Material.AIR
                }
            }
            for (y in 0..7) {
                for (z in 5..12) {
                    Location(
                        Bukkit.getWorld("Replica"),
                        (14 + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                        (66 + y).toDouble(),
                        (z + i * 32).toDouble()
                    ).block.type = Material.AIR
                }
            }
        }
    }

    fun breakPlot(col: Int) {
        val column = col - 1
        for (x in 5..12) {
            for (z in 5..12) {
                Location(
                    Bukkit.getWorld("Replica"),
                    (x + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                    64.0,
                    (z + column * 32).toDouble()
                ).block.type = Material.AIR
            }
        }
    }

    fun drawPlot(col: Int, p: Picture) {
        val column = col - 1
        for (y in 0..7) {
            for (z in 5..12) {
                val b = Location(
                    Bukkit.getWorld("Replica"),
                    (14 + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                    (73 - y).toDouble(),
                    (z + column * 32).toDouble()
                ).block
                b.type = makeClay(p.blocks[Pair(z - 5, y)] ?: 0)
            }
        }
    }

    fun isCompletingPlot(col: Int): Boolean {
        val column = col - 1
        for (x in 0..7) {
            for (y in 0..7) {
                val b = Location(
                    Bukkit.getWorld("Replica"),
                    (14 + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                    (73 - y).toDouble(),
                    (12 - x + column * 32).toDouble()
                ).block
                val b2 = Location(
                    Bukkit.getWorld("Replica"),
                    (12 - y + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                    64.0,
                    (12 - x + column * 32).toDouble()
                ).block
                if (b.type != b2.type) {
                    return false
                }
            }
        }
        return true
    }

    fun loadDraw() {
        loadPlots()
        val picture = Replica.instance?.pictures?.random() ?: return
        var plot = 1
        players.forEach { uuid ->
            val player = Bukkit.getPlayer(uuid) ?: return@forEach
            val zp = Replica.instance?.getPlayer(uuid) ?: return@forEach
            val l = Location(
                Bukkit.getWorld("Replica"),
                (4 + Replica.DISTANCE * 16 * (id - 1)).toDouble(),
                65.0,
                ((plot - 1) * 32 + 9).toDouble()
            )
            l.yaw = -90f
            player.teleport(l)
            player.gameMode = GameMode.SURVIVAL
            zp.plot = plot
            zp.finished = false
            zp.playing = true
            player.inventory.clear()
            player.inventory.addItem(ItemStack(Material.IRON_PICKAXE))
            for (color in picture.colors) {
                player.inventory.addItem(ItemStack(makeClay(color), 64))
            }
            player.updateInventory()
            player.sendMessage("§6" + picture.name)
            plot++
        }
        draw(picture, plot - 1)
    }

    fun draw(picture: Picture, limit: Int) {
        for (i in 1..limit) {
            drawPlot(i, picture)
        }
    }

}
