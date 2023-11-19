package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.usecases.base.ITripleUseCase
import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface IJoinGameUseCase : ITripleUseCase<IGame, Player, ZabriPlayer, Unit>
