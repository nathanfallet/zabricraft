package me.nathanfallet.zabricraft.models.players

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective

data class PlayerScoreboard(
    var objective: Objective?,
    var lastLines: List<String>,
    val name: String
) {

    constructor(name: String) : this(null, listOf(), name)

    val active: Boolean
        get() = objective != null

    fun update(player: Player, newLines: List<String>) {
        if (objective == null) {
            objective = Bukkit.getScoreboardManager()?.newScoreboard?.registerNewObjective(
                name.lowercase(),
                Criteria.DUMMY,
                name
            )
            objective?.displayName = "§6§l$name"
            objective?.displaySlot = DisplaySlot.SIDEBAR
        }
        for (pos in newLines.indices) {
            if (lastLines.size > pos && lastLines[pos] != newLines[pos]) {
                objective?.scoreboard?.resetScores(lastLines[pos])
            }
            objective?.getScore(newLines[pos])?.score = newLines.size - pos
        }
        lastLines = newLines
        objective?.scoreboard?.let {
            player.scoreboard = it
        }
    }

    fun kill() {
        objective?.unregister()
        objective = null
        lastLines = listOf()
    }

}
