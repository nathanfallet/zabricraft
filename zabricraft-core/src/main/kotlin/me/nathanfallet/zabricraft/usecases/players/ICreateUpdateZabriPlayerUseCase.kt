package me.nathanfallet.zabricraft.usecases.players

import dev.kaccelero.usecases.IUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface ICreateUpdateZabriPlayerUseCase : IUseCase<Player, ZabriPlayer?>
