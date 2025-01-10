package dev.zabricraft.usecases.players

import dev.kaccelero.usecases.IUseCase
import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface ICreateUpdateZabriPlayerUseCase : IUseCase<Player, ZabriPlayer?>
