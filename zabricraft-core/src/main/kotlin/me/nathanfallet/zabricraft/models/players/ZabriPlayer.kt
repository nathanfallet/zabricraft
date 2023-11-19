package me.nathanfallet.zabricraft.models.players

import kotlinx.datetime.Instant
import me.nathanfallet.usecases.models.IModel
import org.bukkit.entity.Player
import java.util.*

data class ZabriPlayer(
    override val id: UUID,
    val name: String,
    val password: String,
    val money: Long,
    val score: Long,
    val victories: Long,
    val admin: Boolean,
    val authenticated: Boolean,
    val loginAt: Instant,
    val scoreboard: PlayerScoreboard,
) : IModel<UUID, Player, UpdateZabriPlayerPayload>
