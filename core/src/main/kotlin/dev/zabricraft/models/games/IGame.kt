package dev.zabricraft.models.games

import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

interface IGame {

    val id: Int
    val signs: MutableList<Location>
    var state: GameState
    var currentCountValue: Int

    val name: String
    val players: List<UUID>
    val allPlayers: List<UUID>
    val minPlayers: Int
    val maxPlayers: Int

    fun mainHandler()
    fun start()
    fun stop()
    fun reset()
    fun join(player: Player, zabriPlayer: ZabriPlayer)

}
