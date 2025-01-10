package dev.zabricraft.usecases.games

import dev.kaccelero.usecases.ITripleUseCase
import dev.zabricraft.models.games.IGame
import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface IJoinGameUseCase : ITripleUseCase<IGame, Player, ZabriPlayer, Unit>
