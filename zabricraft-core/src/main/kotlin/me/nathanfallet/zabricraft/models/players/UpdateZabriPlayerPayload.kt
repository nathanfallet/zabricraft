package me.nathanfallet.zabricraft.models.players

import org.bukkit.entity.Player

data class UpdateZabriPlayerPayload(
    val player: Player? = null,
    val password: String? = null,
    val money: Long? = null,
    val score: Long? = null,
    val victories: Long? = null,
    val admin: Boolean? = null
)
