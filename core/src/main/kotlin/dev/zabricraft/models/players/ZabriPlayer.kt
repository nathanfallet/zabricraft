package dev.zabricraft.models.players

import dev.kaccelero.commons.permissions.IPermittee
import dev.kaccelero.models.IModel
import kotlinx.datetime.Instant
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
) : IModel<UUID, Player, UpdateZabriPlayerPayload>, IPermittee
