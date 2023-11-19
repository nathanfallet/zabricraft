package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.usecases.base.IUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface ICreateUpdateZabriPlayerUseCase : IUseCase<Player, ZabriPlayer?>
